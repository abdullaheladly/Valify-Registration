package com.abdullah996.registration.presentation.registrationStepOne

sealed class RegisterStepOneViewState {
    data object ShowLoadingViewState : RegisterStepOneViewState()

    data object Default : RegisterStepOneViewState()

    data class Navigate(val id: Long) : RegisterStepOneViewState()

    data class OnError(val message: String) : RegisterStepOneViewState()
}
