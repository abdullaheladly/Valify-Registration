package com.abdullah996.registration.data.registration.mapper

import com.abdullah996.registration.data.registration.model.UserEntity
import com.abdullah996.registration.domain.registration.model.UserDomainModel
import javax.inject.Inject

class UsersMapper
    @Inject
    constructor() {
        fun mapUserDomainModelToUserEntity(userDomainModel: UserDomainModel): UserEntity {
            return UserEntity(
                username = userDomainModel.username,
                phoneNumber = userDomainModel.phoneNumber,
                email = userDomainModel.email,
                password = userDomainModel.password,
                userImagePath = userDomainModel.userImage,
            )
        }
    }
