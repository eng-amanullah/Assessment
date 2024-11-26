package com.amanullah.assessment.presentation.post

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.amanullah.assessment.base.logger.Logger
import com.amanullah.assessment.data.local.database.dao.UserDao
import com.amanullah.assessment.data.local.database.entity.PostEntity
import com.amanullah.assessment.data.local.database.entity.UserEntity
import com.amanullah.assessment.data.remote.models.BaseRequestModel
import com.amanullah.assessment.data.remote.models.UserResponseModel
import com.amanullah.assessment.presentation.customview.progressbar.LinearProgressBar
import com.amanullah.assessment.presentation.user.UserDetailsActivity
import com.amanullah.assessment.ui.theme.AssessmentTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PostDetailsActivity : ComponentActivity() {

    @Inject
    lateinit var userDao: UserDao

    private val vm by viewModels<PostViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var postId = intent.getIntExtra("id", -1)
        val userId = intent.getIntExtra("userId", -1)

        enableEdgeToEdge()
        setContent {
            AssessmentTheme {
                val scope = rememberCoroutineScope()
                var isLoading by remember { mutableStateOf(value = false) }
                var post by remember { mutableStateOf<PostEntity?>(value = null) }
                var fetchUserDetails by remember { mutableStateOf(value = true) }

                if (fetchUserDetails) {
                    LaunchedEffect(key1 = Unit) {
                        scope.launch {
                            // Submit the selected reason to the ViewModel.
                            val result = vm.getUser(
                                requestModel = BaseRequestModel().apply {
                                    id = userId
                                }
                            )

                            if (result.isError()) {
                                isLoading = false
                                fetchUserDetails = false

                                // TODO handle error
                            } else {
                                val response = result.asSuccess<UserResponseModel>()

                                vm.updatePostAuthor(
                                    id = postId,
                                    author = "${response.firstName} ${response.lastName}"
                                )

                                val postEntities = response.let { user ->
                                    UserEntity(
                                        id = user.id ?: 0,
                                        firstName = user.firstName ?: "",
                                        lastName = user.lastName ?: "",
                                        email = user.email ?: "",
                                        age = user.age ?: 0,
                                        gender = user.gender ?: "",
                                        phone = user.phone ?: "",
                                        username = user.username ?: "",
                                        height = user.height ?: 0.0,
                                        weight = user.weight ?: 0.0,
                                        image = user.image ?: "",
                                        bloodGroup = user.bloodGroup ?: "",
                                        companyName = user.company?.name ?: "",
                                        companyDepartment = user.company?.department ?: "",
                                        companyTitle = user.company?.title ?: "",
                                        address = user.address?.address ?: "",
                                        city = user.address?.city ?: "",
                                        state = user.address?.state ?: "",
                                        country = user.address?.country ?: "",
                                        postalCode = user.address?.postalCode ?: ""
                                    )
                                }
                                // Save to Room
                                scope.launch {
                                    userDao.insertUser(postEntities)
                                }

                                scope.launch {
                                    post = vm.getPostById(id = postId)
                                }

                                Logger.d("post_author", post?.author.toString())

                                isLoading = false
                                fetchUserDetails = false
                            }
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        Column {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(text = "Post Details")
                                }
                            )

                            LinearProgressBar(state = isLoading)
                        }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(paddingValues = innerPadding)
                    ) {
                        if (!isLoading) {
                            post?.let {
                                PostItem(
                                    post = it
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun PostItem(post: PostEntity) {
        ListItem(
            headlineContent = {
                Text(text = post.title ?: "")
            },
            overlineContent = {
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        val intent = Intent(this, UserDetailsActivity::class.java)
                        intent.putExtra("id", post.id)
                        intent.putExtra("userId", post.userId)
                        startActivity(intent)
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = post.author ?: "",
                        textAlign = TextAlign.Start
                    )
                }
            },
            supportingContent = {
                Text(text = post.tags.toString())
            }
        )
    }
}