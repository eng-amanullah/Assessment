package com.amanullah.assessment.data.remote.repositoryimpl

import com.amanullah.assessment.base.appresult.AppResult
import com.amanullah.assessment.base.extensions.executeAPIRequest
import com.amanullah.assessment.data.remote.api.APIService
import com.amanullah.assessment.data.remote.models.BaseRequestModel
import com.amanullah.assessment.data.remote.models.PostsResponseModel
import com.amanullah.assessment.data.remote.models.UserResponseModel
import com.amanullah.assessment.data.remote.models.UsersResponseModel
import com.amanullah.assessment.domain.repository.APIRepository
import javax.inject.Inject

class APIRepositoryImpl @Inject constructor(private val apiService: APIService) : APIRepository {
    override suspend fun getPosts(request: BaseRequestModel): AppResult<PostsResponseModel> =
        executeAPIRequest(
            call = apiService.getPosts(limit = request.limit, skip = request.skip),
            transform = { it },
            default = PostsResponseModel()
        )

    override suspend fun getUsers(request: BaseRequestModel): AppResult<UsersResponseModel> =
        executeAPIRequest(
            call = apiService.getUsers(limit = request.limit, skip = request.skip),
            transform = { it },
            default = UsersResponseModel()
        )

    override suspend fun getUser(request: BaseRequestModel): AppResult<UserResponseModel> =
        executeAPIRequest(
            call = apiService.getUser(id = request.id),
            transform = { it },
            default = UserResponseModel()
        )
}
