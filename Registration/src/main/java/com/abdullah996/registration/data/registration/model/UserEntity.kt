package com.abdullah996.registration.data.registration.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val userImagePath: String?,
)
