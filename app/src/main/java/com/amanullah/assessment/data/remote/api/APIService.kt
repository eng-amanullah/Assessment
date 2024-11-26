package com.amanullah.assessment.data.remote.api

import com.amanullah.assessment.data.remote.models.PostsResponseModel
import com.amanullah.assessment.data.remote.models.UserResponseModel
import com.amanullah.assessment.data.remote.models.UsersResponseModel
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

class APIService @Inject constructor(private val retrofit: Retrofit) :
    APIEndpoint {
    private val api by lazy { retrofit.create(APIEndpoint::class.java) }
    override fun getPosts(limit: Int, skip: Int): Call<PostsResponseModel> =
        api.getPosts(
            limit = limit,
            skip = skip
        )

    override fun getUsers(limit: Int, skip: Int): Call<UsersResponseModel> =
        api.getUsers(
            limit = limit,
            skip = skip
        )

    override fun getUser(id: Int): Call<UserResponseModel> =
        api.getUser(
            id = id
        )
}