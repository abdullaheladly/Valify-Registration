package com.abdullah996.registration.domain.registration.repo

import com.abdullah996.registration.domain.registration.model.UserDomainModel

interface RegistrationRepo {
    suspend fun registerUser(userDomainModel:UserDomainModel):Long
    suspend fun addUserImage(image:String,id:Long)
}