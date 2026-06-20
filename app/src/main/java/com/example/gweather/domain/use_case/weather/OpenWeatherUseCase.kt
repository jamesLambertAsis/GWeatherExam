package com.example.gweather.domain.use_case.weather

data class OpenWeatherUseCase (
    val getWeather: GetCurrentWeather,
    val getWeatherList: GetWeatherList
)