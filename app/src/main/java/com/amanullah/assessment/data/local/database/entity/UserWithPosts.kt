package com.amanullah.assessment.data.local.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithPosts(
    @Embedded val user: UserEntity, // This represents the User entity
    @Relation(
        parentColumn = "id", // Parent column in UserEntity
        entityColumn = "userId" // Foreign key column in PostEntity
    )
    val posts: List<PostEntity> // List of related PostEntity
)
