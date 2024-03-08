package com.abdullah996.registration.domain.registration.usecase

import com.abdullah996.registration.domain.registration.repo.RegistrationRepo
import javax.inject.Inject

class AddImageUseCase
    @Inject
    constructor(private val repo: RegistrationRepo) {
        suspend operator fun invoke(
            image: String,
            id: Long,
        )  {
        }
    }
