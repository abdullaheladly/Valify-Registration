package com.abdullah996.registration.domain.registration.usecase


import com.abdullah996.registration.domain.registration.model.UserDomainModel
import com.abdullah996.registration.domain.registration.repo.RegistrationRepo

class RegisterUseCase(private val repo: RegistrationRepo){
    suspend operator fun invoke(userDomainModel: UserDomainModel) {
    }
}