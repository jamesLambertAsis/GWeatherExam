package com.example.gweather.presentation.screens.login

sealed class LoginScreenState {

    object Loading: LoginScreenState()
    data class Failed(val msg: String): LoginScreenState()
    object SuccessLogin: LoginScreenState()
    object Idle: LoginScreenState()

}