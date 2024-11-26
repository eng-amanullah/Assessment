package com.amanullah.assessment.data.service

import android.app.PendingIntent
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.amanullah.assessment.R
import com.amanullah.assessment.base.extensions.executeAPIRequest
import com.amanullah.assessment.base.logger.Logger
import com.amanullah.assessment.base.utils.Constants
import com.amanullah.assessment.data.local.database.dao.UserDao
import com.amanullah.assessment.data.local.database.entity.UserEntity
import com.amanullah.assessment.data.remote.api.APIService
import com.amanullah.assessment.data.remote.models.UsersResponseModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UsersApiCallService : LifecycleService() {

    @Inject
    lateinit var apiService: APIService

    @Inject
    lateinit var userDao: UserDao

    private var isPaused = false // Variable to track if the API call is paused

    companion object {
        private var limit = Constants.limit
    }

    private val handler = Handler(Looper.getMainLooper())

    private val apiRunnable = object : Runnable {
        override fun run() {
            if (!isPaused) {
                callApi()
                handler.postDelayed(this, 120000) // Reschedule the Runnable after 120 seconds
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Logger.d("SERVICE_LOG", "Created")
        startForegroundService() // Start the service in the foreground
        handler.post(apiRunnable) // Start the periodic task
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("SERVICE_LOG", "Destroy")
        handler.removeCallbacks(apiRunnable) // Clean up the handler to avoid leaks
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Logger.d("SERVICE_LOG", "App removed, stopping service")
        stopSelf()  // Stop the service when the task (app) is removed
    }

    private fun startForegroundService() {
        val toggleIntent = Intent(this, UsersApiCallService::class.java).apply {
            action = "TOGGLE_API"
        }

        val togglePendingIntent: PendingIntent =
            PendingIntent.getService(this, 0, toggleIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val buttonText =
            if (isPaused) "Resume" else "Pause" // Set the button text based on the state

        val notification = NotificationCompat.Builder(this, Constants.channelId)
            .setContentTitle("Users Details Fetching using Background Service")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .addAction(
                R.drawable.ic_launcher_foreground,
                buttonText,
                togglePendingIntent
            )
            .build()

        startForeground(1, notification) // Required for foreground services
    }

    private fun callApi() {
        lifecycleScope.launch(Dispatchers.IO) {
            // Assuming `apiService.getUsers` is a suspend function, and you have access to a `postDao` instance for Room
            try {
                val result = executeAPIRequest(
                    call = apiService.getUsers(limit = limit, skip = 0),
                    transform = { it },
                    default = UsersResponseModel()
                )

                if (!result.isError()) {
                    val response = result.asSuccess<UsersResponseModel>()

                    response.users?.let { users ->
                        // Convert to PostEntity
                        val userEntities = users.map { user ->
                            UserEntity(
                                id = user?.id ?: 0,
                                firstName = user?.firstName ?: "",
                                lastName = user?.lastName ?: "",
                                email = user?.email ?: "",
                                age = user?.age ?: 0,
                                gender = user?.gender ?: "",
                                phone = user?.phone ?: "",
                                username = user?.username ?: "",
                                height = user?.height ?: 0.0,
                                weight = user?.weight ?: 0.0,
                                image = user?.image ?: "",
                                bloodGroup = user?.bloodGroup ?: "",
                                companyName = user?.company?.name ?: "",
                                companyDepartment = user?.company?.department ?: "",
                                companyTitle = user?.company?.title ?: "",
                                address = user?.address?.address ?: "",
                                city = user?.address?.city ?: "",
                                state = user?.address?.state ?: "",
                                country = user?.address?.country ?: "",
                                postalCode = user?.address?.postalCode ?: ""
                            )
                        }

                        // Insert post entities into Room DB
                        userDao.insertUsers(userEntities)

                        // Optionally update limit for pagination
                        limit += 10
                    }
                } else {
                    Logger.e("Error", "API Exception")
                }
            } catch (e: Exception) {
                // Handle any exception that occurred during the API call
                Logger.e("SERVICE", "API Exception: ${e.message}")
            }

        }
    }

    // This function will be triggered when the Toggle action is clicked
    private fun toggleApiCall() {
        isPaused = !isPaused // Toggle the paused state

        // Update the notification with the new button text
        startForegroundService()

        if (isPaused) {
            Logger.d("SERVICE_LOG", "API Call Paused")
        } else {
            Logger.d("SERVICE_LOG", "API Call Resumed")
            handler.post(apiRunnable) // Restart the periodic task if resumed
        }
    }

    // Handling the Toggle Action (Pause/Resume)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "TOGGLE_API" -> toggleApiCall() // Toggle between pause and resume
        }
        return super.onStartCommand(intent, flags, startId)
    }
}

