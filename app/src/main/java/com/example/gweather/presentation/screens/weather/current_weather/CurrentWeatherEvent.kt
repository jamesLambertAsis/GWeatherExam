package com.example.gweather.presentation.screens.weather.current_weather

sealed class CurrentWeatherEvent {

    data class GetCurrentCurrentWeather(val lat: Double, val lon: Double): CurrentWeatherEvent()


}