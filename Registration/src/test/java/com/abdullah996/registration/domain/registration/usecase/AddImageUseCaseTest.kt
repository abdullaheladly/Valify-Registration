package com.abdullah996.registration.domain.registration.usecase

import com.abdullah996.registration.domain.registration.exception.InvalidImageException
import com.abdullah996.registration.domain.registration.repo.RegistrationRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddImageUseCaseTest {
    private lateinit var addImageUseCase: AddImageUseCase

    @Mock
    lateinit var registrationRepo: RegistrationRepo

    @Before
    fun setup() {
        addImageUseCase = AddImageUseCase(registrationRepo)
    }

    @Test(expected = InvalidImageException::class)
    fun `test AddImageUseCase given empty imagePath when invoke addUserImage then throw InvalidImageException`() =
        runTest {
            addImageUseCase.invoke("", 1L)
            verify(registrationRepo, times(0)).addUserImage("", 1L)
        }

    @Test
    fun `test AddImageUseCase given  imagePath and userId when invoke addUserImage then  success`() =
        runTest {
            addImageUseCase.invoke("imagePath", 1L)
            verify(registrationRepo, times(1)).addUserImage("imagePath", 1L)
        }
}
