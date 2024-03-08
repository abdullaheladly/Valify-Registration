package com.abdullah996.registration.presentation.registrationStepTwo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah996.registration.domain.registration.exception.InvalidImageException
import com.abdullah996.registration.domain.registration.usecase.AddImageUseCase
import com.abdullah996.registration.presentation.base.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterStepTwoViewModel
    @Inject
    constructor(
        private val addImageUseCase: AddImageUseCase,
        @IoDispatcher private val defaultDispatcher: CoroutineDispatcher,
    ) : ViewModel() {

        private val mScreenState = MutableSharedFlow<RegisterStepTwoViewState>()
        val screenState = mScreenState.asSharedFlow()

        fun saveUserImage(
            imagePath: String,
            id: Long,
        ) {
        }
    }
