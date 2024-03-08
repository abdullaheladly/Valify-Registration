package com.abdullah996.registration.data.registration.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abdullah996.registration.data.registration.model.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
