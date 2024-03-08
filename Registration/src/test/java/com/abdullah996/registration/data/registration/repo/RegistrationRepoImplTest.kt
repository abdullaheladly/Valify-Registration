package com.abdullah996.registration.data.registration.repo

import com.abdullah996.registration.data.registration.datasource.RegistrationDataSource
import com.abdullah996.registration.data.registration.mapper.UsersMapper
import com.abdullah996.registration.data.registration.model.UserEntity
import com.abdullah996.registration.domain.registration.model.UserDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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
class RegistrationRepoImplTest {
    private lateinit var registrationRepoImpl: RegistrationRepoImpl

    @Mock
    lateinit var dataSource: RegistrationDataSource

    @Before
    fun setup() {
        registrationRepoImpl = RegistrationRepoImpl(dataSource, UsersMapper())
    }

    @Test
    fun `test RegistrationRepo given UserDomainModel when invoke registerUser then return user id in table as long`() =
        runTest {
            val mockedUserDomainModel =
                UserDomainModel("name", "1", "email", "password", null)
            val mockedUserEntity =
                UserEntity(
                    username = "name",
                    phoneNumber = "1",
                    email = "email",
                    password = "password",
                    userImagePath = null,
                )
            whenever(dataSource.registerUser(mockedUserEntity)).thenReturn(1L)
            val result = registrationRepoImpl.registerUser(mockedUserDomainModel)
            verify(dataSource, times(1)).registerUser(mockedUserEntity)
            assertEquals(1L, result)
        }

    @Test
    fun `test RegistrationRepo given imagePath and userId when invoke addUserImage then invoke datasource and success`() =
        runTest {
            registrationRepoImpl.addUserImage("imagePath", 1L)
            verify(dataSource, times(1)).saveUserImage("imagePath", 1L)
        }
}
