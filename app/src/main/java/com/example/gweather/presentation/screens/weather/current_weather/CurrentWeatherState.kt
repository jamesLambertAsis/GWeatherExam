package com.example.gweather.presentation.screens.weather.current_weather

import com.example.gweather.domain.model.LocationDetails

sealed class CurrentWeatherState {

    object Loading : CurrentWeatherState()
    object Idle : CurrentWeatherState()

    data class FetchDetailsSuccess(val locationDetails: LocationDetails) : CurrentWeatherState()

    data class FetchDetailsError(val msg: String) : CurrentWeatherState()


}