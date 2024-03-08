package com.abdullah996.registration.data.registration.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abdullah996.registration.data.registration.model.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity): Long

    @Query("UPDATE user_table SET userImagePath = :newImagePath WHERE id = :userId")
    suspend fun updateUserImage(
        userId: Long,
        newImagePath: String,
    )
}
