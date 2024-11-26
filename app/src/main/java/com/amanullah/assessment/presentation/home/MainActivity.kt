package com.amanullah.assessment.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.amanullah.assessment.base.extensions.reachedBottom
import com.amanullah.assessment.base.utils.Constants
import com.amanullah.assessment.data.local.database.dao.PostDao
import com.amanullah.assessment.data.local.database.dao.UserDao
import com.amanullah.assessment.data.local.database.entity.PostEntity
import com.amanullah.assessment.data.remote.models.BaseRequestModel
import com.amanullah.assessment.data.remote.models.PostsResponseModel
import com.amanullah.assessment.presentation.customview.progressbar.LinearProgressBar
import com.amanullah.assessment.presentation.post.PostDetailsActivity
import com.amanullah.assessment.ui.theme.AssessmentTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var postDao: PostDao

    @Inject
    lateinit var userDao: UserDao

    private val vm by viewModels<MainViewModel>()

    companion object {
        var limitCount = Constants.limit
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssessmentTheme {
                val scope = rememberCoroutineScope()
                var isLoading by remember { mutableStateOf(value = false) }
                var fetchPost by remember { mutableStateOf(value = true) }

                if (fetchPost) {
                    LaunchedEffect(key1 = Unit) {
                        scope.launch {
                            isLoading = true // Set loading state to true.

                            // Submit the selected reason to the ViewModel.
                            val result = vm.getPosts(
                                requestModel = BaseRequestModel().apply {
                                    limit = limitCount
                                }
                            )

                            if (result.isError()) {
                                isLoading = false
                                fetchPost = false

                                // TODO handle error
                            } else {
                                val response = result.asSuccess<PostsResponseModel>()

                                val postEntities = response.posts?.map { post ->
                                    PostEntity(
                                        id = post.id ?: 0,
                                        title = post.title,
                                        body = post.body,
                                        userId = post.userId,
                                        views = post.views,
                                        likes = post.reactions?.likes,
                                        dislikes = post.reactions?.dislikes,
                                        tags = post.tags
                                    )
                                }
                                // Save to Room
                                scope.launch {
                                    if (postEntities != null) {
                                        postDao.insertPosts(postEntities)
                                    }
                                }

                                isLoading = false
                                fetchPost = false
                            }
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        Column {
                            TopAppBar(
                                title = {
                                    Text(text = "List of Post")
                                }
                            )

                            LinearProgressBar(state = isLoading)
                        }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = innerPadding.calculateTopPadding(),
                                bottom = innerPadding.calculateBottomPadding()
                            )
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        //MainScreen(posts = posts)
                        HomeScreen(
                            homeViewModel = vm,
                            callBack = {
                                limitCount += 10
                                fetchPost = true
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun HomeScreen(
        homeViewModel: MainViewModel = hiltViewModel(),
        listState: LazyListState = rememberLazyListState(),
        callBack: () -> Unit
    ) {
        // Collect the paged data as LazyPagingItems
        val posts = homeViewModel.pagedPosts.collectAsLazyPagingItems()

        val reachedBottom: Boolean by remember { derivedStateOf { listState.reachedBottom() } }

        // Load more items when scrolled to the bottom, unless search is enabled
        LaunchedEffect(reachedBottom) {
            if (reachedBottom) callBack() // Load more items if the bottom is reached
        }

        LazyColumn(
            state = listState
        ) {
            itemsIndexed(posts.itemSnapshotList) { index, post ->
                post?.let {
                    PostItem(post = it)
                }

                // Check if we're at the last item and need to load more data
                if (index == posts.itemCount - 1) {
                    // Trigger load more if we are at the end
                    posts.apply {
                        when (loadState.append) {
                            is LoadState.Loading -> {
                                // Do nothing if we're already loading
                            }

                            is LoadState.NotLoading -> {
                                // Load more if not already loading
                                this.retry() // retry loading the next page
                            }

                            is LoadState.Error -> {
                                // Handle error state if needed
                            }
                        }
                    }
                }
            }

            // Loading indicator for the next page
            posts.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { CircularProgressIndicator() }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { CircularProgressIndicator() }
                    }

                    loadState.append is LoadState.Error -> {
                        item { Text("Error loading more posts") }
                    }
                }
            }
        }
    }

    @Composable
    private fun PostItem(post: PostEntity) {
        ListItem(
            modifier = Modifier
                .clickable {
                    val intent = Intent(this, PostDetailsActivity::class.java)
                    intent.putExtra("id", post.id)
                    intent.putExtra("userId", post.userId)
                    startActivity(intent)
                },
            headlineContent = {
                Text(text = post.title ?: "")
            },
            overlineContent = {
                Text(text = post.body ?: "")
            },
            supportingContent = {
                Text(text = post.tags.toString())
            },
            trailingContent = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        )
    }
}