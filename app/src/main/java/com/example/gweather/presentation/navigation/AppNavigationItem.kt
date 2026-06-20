package com.example.gweather.presentation.navigation

import kotlinx.serialization.Serializable

sealed class AppNavigationItem {

    @Serializable
    object LoginScreen

    @Serializable
    object WeatherScreen

}