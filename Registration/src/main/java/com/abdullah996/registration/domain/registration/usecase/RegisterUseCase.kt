package com.abdullah996.registration.domain.registration.usecase

import com.abdullah996.registration.domain.registration.exception.EmptyFieldException
import com.abdullah996.registration.domain.registration.model.UserDomainModel
import com.abdullah996.registration.domain.registration.repo.RegistrationRepo
import javax.inject.Inject

class RegisterUseCase
    @Inject
    constructor(private val repo: RegistrationRepo) {
        suspend operator fun invoke(userDomainModel: UserDomainModel): Long {
            validateInputs(userDomainModel)
            return repo.registerUser(userDomainModel)
        }

        private fun validateInputs(userDomainModel: UserDomainModel) {
            if (userDomainModel.username.isEmpty()) {
                throw EmptyFieldException("Username cannot be empty.")
            }
            if (userDomainModel.phoneNumber.isEmpty()) {
                throw EmptyFieldException("Phone number cannot be empty.")
            }
            if (userDomainModel.email.isEmpty()) {
                throw EmptyFieldException("Email cannot be empty.")
            }
            if (userDomainModel.password.isEmpty()) {
                throw EmptyFieldException("Password cannot be empty.")
            }
        }
    }
