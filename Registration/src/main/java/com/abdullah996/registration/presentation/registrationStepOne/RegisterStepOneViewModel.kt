package com.abdullah996.registration.presentation.registrationStepOne

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah996.registration.domain.registration.exception.EmptyFieldException
import com.abdullah996.registration.domain.registration.model.UserDomainModel
import com.abdullah996.registration.domain.registration.usecase.RegisterUseCase
import com.abdullah996.registration.presentation.base.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterStepOneViewModel
    @Inject
    constructor(
        private val registerUseCase: RegisterUseCase,
        @IoDispatcher private val defaultDispatcher: CoroutineDispatcher,
    ) : ViewModel() {
        private val argumentsExceptionHandlerHandler =
            CoroutineExceptionHandler { _, e ->
                viewModelScope.launch(defaultDispatcher) {
                    onFailure(e)
                }
            }
        val username = MutableStateFlow("")

        val phoneNumber = MutableStateFlow("")

        val email = MutableStateFlow("")

        val password = MutableStateFlow("")

        private val mScreenState = MutableSharedFlow<RegisterStepOneViewState>()
        val screenState = mScreenState.asSharedFlow()

        fun registerUser() {
            viewModelScope.launch(argumentsExceptionHandlerHandler + defaultDispatcher) {
                val userEntity =
                    UserDomainModel(
                        username = username.value,
                        phoneNumber = phoneNumber.value,
                        email = email.value,
                        password = password.value,
                        userImage = null,
                    )
                val result = registerUseCase.invoke(userEntity)
                mScreenState.emit(RegisterStepOneViewState.Navigate(result))
            }
        }

        private suspend fun onFailure(throwable: Throwable)  {
            when (throwable) {
                is EmptyFieldException -> {
                    mScreenState.emit(RegisterStepOneViewState.OnError(throwable.message))
                }
                else -> {
                    mScreenState.emit(RegisterStepOneViewState.OnError(throwable.message.toString()))
                }
            }
        }
    }
