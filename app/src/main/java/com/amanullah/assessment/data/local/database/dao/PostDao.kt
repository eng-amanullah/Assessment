package com.amanullah.assessment.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amanullah.assessment.data.local.database.entity.PostEntity

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun getPostById(id: Int): PostEntity?

    @Query("SELECT * FROM posts")
    suspend fun getAllPosts(): List<PostEntity>

    @Query("SELECT * FROM posts ORDER BY id ASC")
    fun getPagedPosts(): PagingSource<Int, PostEntity>

    @Query("UPDATE posts SET author = :author WHERE id = :id")
    suspend fun updatePostAuthor(id: Int, author: String)
}
