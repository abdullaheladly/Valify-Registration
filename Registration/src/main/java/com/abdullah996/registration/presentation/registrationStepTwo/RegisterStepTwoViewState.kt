package com.abdullah996.registration.presentation.registrationStepTwo

sealed class RegisterStepTwoViewState {
    data object ShowLoadingViewState : RegisterStepTwoViewState()

    data object Default : RegisterStepTwoViewState()

    data object Navigate : RegisterStepTwoViewState()

    data class OnError(val message: String) : RegisterStepTwoViewState()
}
