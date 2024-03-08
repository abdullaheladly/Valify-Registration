package com.abdullah996.registration.data.registration.datasource

import com.abdullah996.registration.data.registration.model.UserEntity

interface RegistrationDataSource {
    suspend fun registerUser(userEntity: UserEntity): Long

    suspend fun saveUserImage(
        image: String,
        id: Long,
    )
}
