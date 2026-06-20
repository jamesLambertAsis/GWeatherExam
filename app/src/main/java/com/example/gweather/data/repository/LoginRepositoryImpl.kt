package com.example.gweather.data.repository

import com.example.gweather.core.TaskResult
import com.example.gweather.data.local.dao.UserDao
import com.example.gweather.data.local.entity.UserEntity
import com.example.gweather.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val dao: UserDao
): LoginRepository {

    override suspend fun getUserByUserName(userName: String): UserEntity? {
        return dao.getUserByUsername(userName)
    }

    override suspend fun register(
        userName: String,
        password: String
    ): TaskResult<Unit> {
        return try {
            dao.insertUser(
                UserEntity(
                    userName = userName,
                    password = password
                )
            )
            TaskResult.Success(Unit)
        } catch (e: Exception) {
            TaskResult.Error(errorMessage = e.message ?: "Could not save user to database")
        }
    }
}