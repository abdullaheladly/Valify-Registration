package com.abdullah996.registration.data.registration.mapper

import com.abdullah996.registration.data.registration.model.UserEntity
import com.abdullah996.registration.domain.registration.model.UserDomainModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UsersMapperTest {
    private lateinit var usersMapper: UsersMapper

    @Before
    fun setup() {
        usersMapper = UsersMapper()
    }

    @Test
    fun `test usersMapper given userDomainModel when invoke mapUserDomainModelToUserEntity then return UsersEntity`() =
        runTest {
            val mockedUserDomainModel =
                UserDomainModel("name", "1", "email", "password", null)
            val expectedEntity =
                UserEntity(
                    username = "name",
                    phoneNumber = "1",
                    email = "email",
                    password = "password",
                    userImagePath = null,
                )
            val result = usersMapper.mapUserDomainModelToUserEntity(mockedUserDomainModel)
            Assert.assertEquals(expectedEntity, result)
        }
}
