package com.example.gweather.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gweather.core.TaskResult
import com.example.gweather.domain.use_case.user.UserUseCase
import com.example.gweather.presentation.screens.login.LoginScreenState.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userUseCase: UserUseCase,
): ViewModel() {

    private val _state = MutableStateFlow<LoginScreenState>(Idle)

    val state = _state.asStateFlow()

    fun resetState() {
        _state.value = Idle
    }

    fun onEvent(event: LoginEvent) {
        when (event) {

            is LoginEvent.Login -> {
                viewModelScope.launch {
                    _state.value = Loading
                    val result = userUseCase.loginUser(event.userEntity)

                    if (result is TaskResult.Success){
                        _state.value = SuccessLogin
                    } else if (result is TaskResult.Error){
                        _state.value = Failed(result.errorMessage)
                    }
                }
            }

            is LoginEvent.Register -> {
                viewModelScope.launch {
                    _state.value = Loading
                    val result = userUseCase.registerUser(event.userEntity)
                    if (result is TaskResult.Success){
                        _state.value = SuccessLogin
                    } else if (result is TaskResult.Error){
                        _state.value = Failed(result.errorMessage)
                    }
                }
            }
            LoginEvent.ResetState -> {
                resetState()
            }
        }
    }
}