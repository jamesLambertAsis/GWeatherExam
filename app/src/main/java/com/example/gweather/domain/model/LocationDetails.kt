package com.example.gweather.domain.model

data class LocationDetails(
    val country: String = "",
    val city: String = "",
    val sunrise: String = "",
    val sunset: String = "",
    val temp: String = "",
    val currentWeather: String = "",
    val icon: String = ""
)