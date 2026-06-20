package com.example.gweather.domain.use_case.user

import com.example.gweather.core.TaskResult
import com.example.gweather.data.local.entity.UserEntity
import com.example.gweather.decodeFromBase64
import com.example.gweather.domain.model.User
import com.example.gweather.domain.repository.LoginRepository

class LoginUser(
    private val repository: LoginRepository
) {

    suspend operator fun invoke(newUser: User): TaskResult<Boolean> {
        val user = repository.getUserByUserName(newUser.userName)
        return if (user != null) {
            if (user.password.decodeFromBase64() != newUser.password.decodeFromBase64()) {
                TaskResult.Error(errorMessage = "Incorrect Password")
            } else {
                TaskResult.Success(true)
            }
        } else {
            TaskResult.Error(errorMessage = "Username not found")
        }
    }
}

