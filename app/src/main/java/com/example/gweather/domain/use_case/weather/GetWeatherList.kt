package com.example.gweather.domain.use_case.weather

import com.example.gweather.core.TaskResult
import com.example.gweather.domain.model.WeatherDetails
import com.example.gweather.domain.repository.OpenWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWeatherList (
    private val repository: OpenWeatherRepository
) {

    operator fun invoke(): Flow<TaskResult<List<WeatherDetails>>> = flow {
        emit(TaskResult.Loading)
        try {
            val result = repository.getWeathers()
            emit(TaskResult.Success(result))
        } catch (e: Exception) {
            emit(TaskResult.Error(errorMessage = e.message ?: "Unknown error"))
        }

    }

}