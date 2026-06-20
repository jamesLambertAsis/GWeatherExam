package com.example.gweather.presentation.screens.login

import com.example.gweather.data.local.entity.UserEntity
import com.example.gweather.domain.model.User

sealed class LoginEvent {

    data class Login(val userEntity: User): LoginEvent()
    data class Register(val userEntity: User): LoginEvent()

    object ResetState: LoginEvent()

}