package com.example.gweather.domain.use_case.user

import com.example.gweather.core.TaskResult
import com.example.gweather.data.local.entity.UserEntity
import com.example.gweather.domain.model.User
import com.example.gweather.domain.repository.LoginRepository

class RegisterUser(
    private val repository: LoginRepository
) {

    suspend operator fun invoke(newUser: User): TaskResult<Unit> {
        val localUser = repository.getUserByUserName(newUser.userName)
        return if (localUser != null) {
            TaskResult.Error(errorMessage = "User already exists")
        } else {
            repository.register(userName = newUser.userName, password = newUser.password)
        }

    }

}