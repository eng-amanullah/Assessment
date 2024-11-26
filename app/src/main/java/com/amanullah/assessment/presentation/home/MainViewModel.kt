package com.amanullah.assessment.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.amanullah.assessment.base.appresult.AppResult
import com.amanullah.assessment.data.local.database.dao.PostDao
import com.amanullah.assessment.data.remote.models.BaseRequestModel
import com.amanullah.assessment.data.remote.models.PostsResponseModel
import com.amanullah.assessment.domain.repository.APIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val postDao: PostDao,
    private val repository: APIRepository
) : ViewModel() {
    suspend fun getPosts(requestModel: BaseRequestModel): AppResult<PostsResponseModel> =
        withContext(context = Dispatchers.IO) {
            repository.getPosts(request = requestModel)
        }

    val pagedPosts = Pager(
        PagingConfig(pageSize = 10, prefetchDistance = 1, enablePlaceholders = false)
    ) {
        postDao.getPagedPosts()
    }.flow.cachedIn(viewModelScope)
}