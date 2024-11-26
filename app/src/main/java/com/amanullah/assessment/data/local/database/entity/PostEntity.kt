package com.amanullah.assessment.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String?,
    val body: String?,
    val userId: Int?,
    val views: Int?,
    val likes: Int?,
    val dislikes: Int?,
    val tags: List<String>?,
    val author: String? = null
)
