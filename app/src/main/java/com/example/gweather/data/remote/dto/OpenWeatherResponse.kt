package com.example.gweather.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class OpenWeatherResponse(
    val main: Main,
    val weather: List<WeatherInfo>,
    val name: String, // City name
    val sys: Sys,
) {
    val iconCode: String? get() = weather.firstOrNull()?.icon
}

@Serializable
data class Main(
    val temp: Double,
    val humidity: Int
)

@Serializable
data class WeatherInfo(
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
