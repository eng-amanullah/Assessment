package com.amanullah.assessment.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val age: Int?,
    val gender: String?,
    val phone: String?,
    val username: String?,
    val height: Double?,
    val weight: Double?,
    val image: String?,
    val bloodGroup: String?,
    val companyName: String?,
    val companyDepartment: String?,
    val companyTitle: String?,
    val address: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val postalCode: String?
)