package com.amanullah.assessment.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amanullah.assessment.base.converter.Converters
import com.amanullah.assessment.data.local.database.dao.PostDao
import com.amanullah.assessment.data.local.database.dao.UserDao
import com.amanullah.assessment.data.local.database.entity.PostEntity
import com.amanullah.assessment.data.local.database.entity.UserEntity

@Database(entities = [PostEntity::class, UserEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    // New DAO for users
    abstract fun userDao(): UserDao
}
