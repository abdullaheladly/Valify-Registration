package com.abdullah996.registration.data.registration.datasource

import com.abdullah996.registration.data.registration.model.UserEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class RegistrationLocalDataSourceTest {
    private lateinit var registrationLocalDataSource: RegistrationLocalDataSource

    @Mock
    lateinit var dao: UserDao

    @Before
    fun setup() {
        registrationLocalDataSource = RegistrationLocalDataSource(dao)
    }

    @Test
    fun `test RegistrationLocalDataSource given UserEntity when invoke registerUser then return user id in database in long`() =
        runTest {
            val mockedUserEntity =
                UserEntity(
                    username = "name",
                    phoneNumber = "1",
                    email = "email",
                    password = "password",
                    userImagePath = null,
                )
            whenever(dao.insertUser(mockedUserEntity)).thenReturn(1L)
            val result = registrationLocalDataSource.registerUser(mockedUserEntity)
            verify(dao, times(1)).insertUser(mockedUserEntity)
            Assert.assertEquals(1L, result)
        }
}
