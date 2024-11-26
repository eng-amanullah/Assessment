package com.amanullah.assessment.presentation.post

import androidx.lifecycle.ViewModel
import com.amanullah.assessment.base.appresult.AppResult
import com.amanullah.assessment.data.local.database.dao.PostDao
import com.amanullah.assessment.data.local.database.entity.PostEntity
import com.amanullah.assessment.data.remote.models.BaseRequestModel
import com.amanullah.assessment.data.remote.models.UserResponseModel
import com.amanullah.assessment.domain.repository.APIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postDao: PostDao,
    private val repository: APIRepository
) : ViewModel() {
    suspend fun getUser(requestModel: BaseRequestModel): AppResult<UserResponseModel> =
        withContext(context = Dispatchers.IO) {
            repository.getUser(request = requestModel)
        }

    suspend fun getPostById(id: Int): PostEntity? {
        return postDao.getPostById(id = id)
    }

    suspend fun updatePostAuthor(id: Int, author: String) {
        return postDao.updatePostAuthor(id = id, author = author)
    }
}