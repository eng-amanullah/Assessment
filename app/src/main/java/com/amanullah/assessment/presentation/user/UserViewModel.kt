package com.amanullah.assessment.presentation.user

import androidx.lifecycle.ViewModel
import com.amanullah.assessment.data.local.database.dao.UserDao
import com.amanullah.assessment.data.local.database.entity.UserEntity
import com.amanullah.assessment.data.local.database.entity.UserWithPosts
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDao: UserDao,
) : ViewModel() {
    suspend fun getUserById(id: Int): UserEntity? {
        return userDao.getUserById(id = id)
    }

    suspend fun getUserAndPostById(id: Int): UserWithPosts {
        return userDao.getUserWithPosts(userId = id)
    }
}