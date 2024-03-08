package com.abdullah996.registration.data.registration.datasource

import com.abdullah996.registration.data.registration.model.UserEntity
import javax.inject.Inject

class RegistrationLocalDataSource
    @Inject
    constructor(private val dao: UserDao) : RegistrationDataSource {
        override suspend fun registerUser(userEntity: UserEntity): Long {
            return dao.insertUser(userEntity)
        }

        override suspend fun saveUserImage(
            image: String,
            id: Long,
        ) {
            return dao.updateUserImage(id, image)
        }
    }
