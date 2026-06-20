package com.example.gweather.core

sealed class TaskResult<out T> {

    object Loading: TaskResult<Nothing>()

    data class Success<T>(val data: T): TaskResult<T>()

    data class Error<T>(val error: T? = null, val errorMessage: String): TaskResult<T>()

}