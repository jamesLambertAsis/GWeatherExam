package com.example.gweather.domain.repository

import com.example.gweather.core.TaskResult
import com.example.gweather.data.local.entity.UserEntity

interface LoginRepository {

    suspend fun getUserByUserName(userName: String): UserEntity?

    suspend fun register(
        userName: String,
        password: String
    ): TaskResult<Unit>

}