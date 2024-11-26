package com.amanullah.assessment.base.networkmanager

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import com.amanullah.assessment.base.extensions.isHostReachable
import com.amanullah.assessment.base.logger.Logger

/**
 * Responsible to check device internet connection status across the application lifecycle.
 * Only works when app is in the foreground only.
 */
class ConnectivityChecker private constructor(private val application: Application) {
    companion object {
        private const val TAG = "ConnectivityChecker"
    }

    object Installer {
        private var cc: ConnectivityChecker? = null

        fun init(application: Application) {
            cc = ConnectivityChecker(application)
        }
    }

    // Callback for activity lifecycle for this specific application
    private val activityCallback = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            try {
                getConnectivityManager(application).apply {
                    registerNetworkCallback(
                        getNetworkRequest(),
                        getNetworkCallBack
                    )
                    Logger.d(TAG, "onActivityResumed: listener registered")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Logger.d(TAG, e.message ?: "")
            }
        }

        override fun onActivityStarted(activity: Activity) {}

        override fun onActivityResumed(activity: Activity) {}

        override fun onActivityPaused(activity: Activity) {}

        override fun onActivityStopped(activity: Activity) {}

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {
            try {
                getConnectivityManager(application).unregisterNetworkCallback(getNetworkCallBack)
                Logger.d(TAG, "onActivityPaused: listener unregistered")
            } catch (e: Exception) {
                e.printStackTrace()
                Logger.d(TAG, e.message ?: "")
            }
        }

    }

    // Initialize the Manager
    init {
        try {
            getConnectivityManager(application)
            application.registerActivityLifecycleCallbacks(activityCallback)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Get ConnectivityManager which is a system service
    private fun getConnectivityManager(application: Application) =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Provide the network request
    private fun getNetworkRequest(): NetworkRequest {
        return NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_VPN)
            .build()
    }

    // Callback to get notified about network availability
    private val getNetworkCallBack = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            updateInternetConnection()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            updateInternetConnection()
        }
    }

    private fun updateInternetConnection() {
        try {
            NetworkManager.setInternetConnection(isHostReachable())
        } catch (e: Exception) {
            NetworkManager.setInternetConnection(false)
        }
    }
}