package com.abdullah996.registration.domain.registration.model

data class UserDomainModel(
    val username: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val userImage:String?
)
