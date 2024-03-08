package com.abdullah996.registration.data.registration.repo

import com.abdullah996.registration.data.registration.datasource.RegistrationDataSource
import com.abdullah996.registration.data.registration.mapper.UsersMapper
import com.abdullah996.registration.domain.registration.model.UserDomainModel
import com.abdullah996.registration.domain.registration.repo.RegistrationRepo
import javax.inject.Inject

class RegistrationRepoImpl
    @Inject
    constructor(private val dataSource: RegistrationDataSource, private val mapper: UsersMapper) : RegistrationRepo {
        override suspend fun registerUser(userDomainModel: UserDomainModel): Long {
            return dataSource.registerUser(mapper.mapUserDomainModelToUserEntity(userDomainModel))
        }

        override suspend fun addUserImage(
            image: String,
            id: Long,
        ) {
            return dataSource.saveUserImage(image, id)
        }
    }
