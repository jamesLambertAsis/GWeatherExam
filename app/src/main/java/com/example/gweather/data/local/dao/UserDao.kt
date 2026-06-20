package com.example.gweather.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gweather.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM Users WHERE userName = :username")
    suspend fun getUserByUsername(username: String): UserEntity?
}