package com.amanullah.assessment.data.remote.models

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class PostsResponseModel {
    @SerializedName("posts")
    @Expose
    var posts: MutableList<Post>? = null

    @SerializedName("total")
    @Expose
    var total: Int? = null

    @SerializedName("skip")
    @Expose
    var skip: Int? = null

    @SerializedName("limit")
    @Expose
    var limit: Int? = null

    companion object {
        @Keep
        class Post {
            @SerializedName("id")
            @Expose
            var id: Int? = null

            @SerializedName("title")
            @Expose
            var title: String? = null

            @SerializedName("body")
            @Expose
            var body: String? = null

            @SerializedName("tags")
            @Expose
            var tags: MutableList<String>? = null

            @SerializedName("reactions")
            @Expose
            var reactions: Reactions? = null

            @SerializedName("views")
            @Expose
            var views: Int? = null

            @SerializedName("userId")
            @Expose
            var userId: Int? = null
        }

        @Keep
        class Reactions {
            @SerializedName("likes")
            @Expose
            var likes: Int? = null

            @SerializedName("dislikes")
            @Expose
            var dislikes: Int? = null
        }
    }
}