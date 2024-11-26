package com.amanullah.assessment.presentation.user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.amanullah.assessment.data.local.database.dao.UserDao
import com.amanullah.assessment.data.local.database.entity.UserWithPosts
import com.amanullah.assessment.presentation.customview.progressbar.LinearProgressBar
import com.amanullah.assessment.ui.theme.AssessmentTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UserDetailsActivity : ComponentActivity() {

    @Inject
    lateinit var userDao: UserDao

    private val vm by viewModels<UserViewModel>()

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
                var user by remember { mutableStateOf<UserWithPosts?>(value = null) }
                var fetchUserDetails by remember { mutableStateOf(value = true) }

                if (fetchUserDetails) {
                    LaunchedEffect(key1 = Unit) {
                        scope.launch {
                            isLoading = true
                            user = vm.getUserAndPostById(id = userId)
                            isLoading = false
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
                                    Text(text = "User Details")
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
                            user?.let {
                                PostItem(post = it)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun PostItem(post: UserWithPosts) {
        ListItem(
            headlineContent = {
                Text(text = "${post.user.firstName} ${post.user.firstName}")
            },
            overlineContent = {
                Text(text = post.user.email ?: "")
            },
            supportingContent = {
                Column {
                    repeat(post.posts.size) { index ->
                        Text(text = post.posts[index].title ?: "")
                    }
                }
            },
            leadingContent = {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(size = 50.dp),
                    model = post.user.image ?: "",
                    contentDescription = null
                )
            }
        )
    }
}