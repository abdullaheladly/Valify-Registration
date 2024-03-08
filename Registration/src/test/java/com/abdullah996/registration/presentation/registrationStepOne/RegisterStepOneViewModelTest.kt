package com.abdullah996.registration.presentation.registrationStepOne

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abdullah996.registration.domain.registration.exception.EmptyFieldException
import com.abdullah996.registration.domain.registration.model.UserDomainModel
import com.abdullah996.registration.domain.registration.usecase.RegisterUseCase
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
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegisterStepOneViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var registerStepOneViewModel: RegisterStepOneViewModel

    @Mock
    lateinit var useCase: RegisterUseCase

    @Before
    fun setup()  {
        registerStepOneViewModel = RegisterStepOneViewModel(useCase, testDispatcher)
    }

    @Test
    fun `test RegisterStepOneViewModel given invalid inputs when invoke register throw EmptyFieldException emits in screen states error`() =
        runTest {
            val mockedUser = UserDomainModel("", "", "", "", null)

            whenever(useCase.invoke(mockedUser)).doAnswer {
                throw EmptyFieldException("")
            }

            val job =
                launch {
                    registerStepOneViewModel.registerUser()
                }

            try {
                val result = registerStepOneViewModel.screenState.first()

                verify(useCase, times(1)).invoke(mockedUser)
                assertEquals(RegisterStepOneViewState.OnError(""), result)
            } finally {
                job.cancelAndJoin()
            }
        }

    @Test
    fun `test RegisterStepOneViewModel when invoke register throw exception emits in screen states error`() =
        runTest {
            val mockedUser = UserDomainModel("", "", "", "", null)

            whenever(useCase.invoke(mockedUser)).doAnswer {
                throw Exception("")
            }

            val job =
                launch {
                    registerStepOneViewModel.registerUser()
                }

            try {
                val result = registerStepOneViewModel.screenState.first()

                verify(useCase, times(1)).invoke(mockedUser)
                assertEquals(RegisterStepOneViewState.OnError(""), result)
            } finally {
                job.cancelAndJoin()
            }
        }

    @Test
    fun `test RegisterStepOneViewModel given valid inputs when invoke register  emits in screen states navigate`() =
        runTest {
            val mockedUser = UserDomainModel("", "", "", "", null)

            whenever(useCase.invoke(mockedUser)).doReturn(1L)

            val job =
                launch {
                    registerStepOneViewModel.registerUser()
                }

            try {
                val result = registerStepOneViewModel.screenState.first()

                verify(useCase, times(1)).invoke(mockedUser)
                assertEquals(RegisterStepOneViewState.Navigate(1L), result)
            } finally {
                job.cancelAndJoin()
            }
        }
}
