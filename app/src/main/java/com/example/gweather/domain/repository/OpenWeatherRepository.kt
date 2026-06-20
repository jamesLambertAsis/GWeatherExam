package com.example.gweather.domain.repository

import com.example.gweather.data.remote.dto.OpenWeatherResponse
import com.example.gweather.domain.model.WeatherDetails

interface OpenWeatherRepository {

    suspend fun getWeather(lat: Double, lon: Double): OpenWeatherResponse

    suspend fun saveWeather(
        weather: String,
        time: String,
        icon: String
    )

    suspend fun getWeathers(): List<WeatherDetails>

}