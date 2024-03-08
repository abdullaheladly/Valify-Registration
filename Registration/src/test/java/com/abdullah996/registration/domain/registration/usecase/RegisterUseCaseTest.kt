package com.abdullah996.registration.domain.registration.usecase

import com.abdullah996.registration.domain.registration.exception.EmptyFieldException
import com.abdullah996.registration.domain.registration.model.UserDomainModel
import com.abdullah996.registration.domain.registration.repo.RegistrationRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegisterUseCaseTest {

    private lateinit var registerUseCase: RegisterUseCase

    @Mock
    lateinit var registrationRepo: RegistrationRepo

    @Before
    fun setup(){
        registerUseCase= RegisterUseCase(registrationRepo)
    }

    @Test(expected = EmptyFieldException::class)
    fun `test RegisterUseCaseTest given empty username when invoke then throw EmptyFieldException`() =
        runTest {
            val mockedUser=UserDomainModel("","0","a","a",null)
            registerUseCase.invoke(mockedUser)
            verify(registrationRepo, times(0)).registerUser(mockedUser)
        }

    @Test(expected = EmptyFieldException::class)
    fun `test RegisterUseCaseTest given empty email when invoke then throw EmptyFieldException`() =
        runTest {
            val mockedUser=UserDomainModel("a","0","","a",null)
            registerUseCase.invoke(mockedUser)
            verify(registrationRepo, times(0)).registerUser(mockedUser)
        }

    @Test(expected = EmptyFieldException::class)
    fun `test RegisterUseCaseTest given empty phone number when invoke then throw EmptyFieldException`() =
        runTest {
            val mockedUser=UserDomainModel("a","","a","a",null)
            registerUseCase.invoke(mockedUser)
            verify(registrationRepo, times(0)).registerUser(mockedUser)
        }

    @Test(expected = EmptyFieldException::class)
    fun `test RegisterUseCaseTest given empty password when invoke then throw EmptyFieldException`() =
        runTest {
            val mockedUser=UserDomainModel("a","0","a","",null)
            registerUseCase.invoke(mockedUser)
            verify(registrationRepo, times(0)).registerUser(mockedUser)
        }

    @Test
    fun `test RegisterUseCaseTest valid inputs empty username when invoke then return user id in table as long`() =
        runTest {
            val mockedUser=UserDomainModel("a","0","a","a",null)

            whenever(registrationRepo.registerUser(mockedUser)).thenReturn(1L)
            val result=registerUseCase.invoke(mockedUser)
            verify(registrationRepo, times(1)).registerUser(mockedUser)
            Assert.assertEquals(result,1L)
        }

}