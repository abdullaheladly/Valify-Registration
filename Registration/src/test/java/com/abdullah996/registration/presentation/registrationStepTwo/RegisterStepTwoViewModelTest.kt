package com.abdullah996.registration.presentation.registrationStepTwo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abdullah996.registration.domain.registration.exception.EmptyFieldException
import com.abdullah996.registration.domain.registration.usecase.AddImageUseCase
import com.abdullah996.registration.presentation.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegisterStepTwoViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var registerStepTwoViewModel: RegisterStepTwoViewModel

    @Mock
    lateinit var useCase: AddImageUseCase

    @Before
    fun setup() {
        registerStepTwoViewModel = RegisterStepTwoViewModel(useCase, testDispatcher)
    }

    @Test
    fun `test registerStepTwoViewModel when invoke addUserImage throw InvalidImageException emits in screen states error`() =
        runTest {
            whenever(useCase.invoke("", 1L)).doAnswer {
                throw EmptyFieldException("")
            }

            val job =
                launch {
                    registerStepTwoViewModel.saveUserImage("", 1L)
                }

            try {
                val result = registerStepTwoViewModel.screenState.first()

                verify(useCase, times(1)).invoke("", 1L)
                assertEquals(RegisterStepTwoViewState.OnError(""), result)
            } finally {
                job.cancelAndJoin()
            }
        }

    @Test
    fun `test registerStepTwoViewModel when invoke addUserIMage throw exception emits in screen states error`() =
        runTest {
            whenever(useCase.invoke("imagePath", 1L)).doAnswer {
                throw Exception("")
            }

            val job =
                launch {
                    registerStepTwoViewModel.saveUserImage("imagePath", 1L)
                }

            try {
                val result = registerStepTwoViewModel.screenState.first()

                verify(useCase, times(1)).invoke("imagePath", 1L)
                assertEquals(RegisterStepTwoViewState.OnError(""), result)
            } finally {
                job.cancelAndJoin()
            }
        }

    @Test
    fun `test RegisterStepOneViewModel given valid inputs when invoke register  emits in screen states navigate`() =
        runTest {
            val job =
                launch {
                    registerStepTwoViewModel.saveUserImage("imagePath", 0L)
                }

            try {
                val result = registerStepTwoViewModel.screenState.first()

                verify(useCase, times(1)).invoke("imagePath", 0L)
                assertEquals(RegisterStepTwoViewState.Navigate, result)
            } finally {
                job.cancelAndJoin()
            }
        }
}
