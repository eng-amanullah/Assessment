package com.amanullah.assessment.domain.repository

import com.amanullah.assessment.base.appresult.AppResult
import com.amanullah.assessment.data.remote.models.BaseRequestModel
import com.amanullah.assessment.data.remote.models.PostsResponseModel
import com.amanullah.assessment.data.remote.models.UserResponseModel
import com.amanullah.assessment.data.remote.models.UsersResponseModel

interface APIRepository {
    suspend fun getPosts(request: BaseRequestModel): AppResult<PostsResponseModel>
    suspend fun getUsers(request: BaseRequestModel): AppResult<UsersResponseModel>
    suspend fun getUser(request: BaseRequestModel): AppResult<UserResponseModel>
}