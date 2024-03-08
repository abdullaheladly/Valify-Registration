package com.abdullah996.registration.domain.registration.usecase

import com.abdullah996.registration.domain.registration.exception.InvalidImageException
import com.abdullah996.registration.domain.registration.repo.RegistrationRepo
import javax.inject.Inject

class AddImageUseCase
    @Inject
    constructor(private val repo: RegistrationRepo) {
        suspend operator fun invoke(
            image: String,
            id: Long,
        ) {
            validateUserImagePath(image)
            repo.addUserImage(image, id)
        }

        private fun validateUserImagePath(imagePath: String) {
            if (imagePath.isEmpty()) {
                throw InvalidImageException("Invalid image path")
            }
        }
    }
