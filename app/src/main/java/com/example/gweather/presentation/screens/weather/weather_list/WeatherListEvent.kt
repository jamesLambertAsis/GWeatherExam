package com.example.gweather.presentation.screens.weather.weather_list

sealed class WeatherListEvent {
    object GetWeatherList : WeatherListEvent()
}