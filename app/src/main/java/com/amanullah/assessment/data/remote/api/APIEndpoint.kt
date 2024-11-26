package com.amanullah.assessment.data.remote.api

import com.amanullah.assessment.data.remote.models.PostsResponseModel
import com.amanullah.assessment.data.remote.models.UserResponseModel
import com.amanullah.assessment.data.remote.models.UsersResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface APIEndpoint {
    companion object {
        private const val POSTS = "posts"
        private const val USERS = "users"
        private const val USER = "users/{id}"
    }

    @Headers("Content-Type: application/json")
    @GET(POSTS)
    fun getPosts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
    ): Call<PostsResponseModel>

    @Headers("Content-Type: application/json")
    @GET(USERS)
    fun getUsers(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
    ): Call<UsersResponseModel>

    @Headers("Content-Type: application/json")
    @GET(USER)
    fun getUser(
        @Path(value = "id", encoded = true) id: Int,
    ): Call<UserResponseModel>
}