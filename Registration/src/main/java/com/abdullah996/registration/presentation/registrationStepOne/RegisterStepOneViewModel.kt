package com.abdullah996.registration.presentation.registrationStepOne

import androidx.lifecycle.ViewModel
import com.abdullah996.registration.domain.registration.usecase.RegisterUseCase
import com.abdullah996.registration.presentation.base.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class RegisterStepOneViewModel
    @Inject
    constructor(
        private val registerUseCase: RegisterUseCase,
        @IoDispatcher private val defaultDispatcher: CoroutineDispatcher,
    ) : ViewModel() {
        val username = MutableStateFlow("")

        val phoneNumber = MutableStateFlow("")

        val email = MutableStateFlow("")

        val password = MutableStateFlow("")

        private val mScreenState = MutableSharedFlow<RegisterStepOneViewState>()
        val screenState = mScreenState.asSharedFlow()

        fun registerUser() {
        }
    }
