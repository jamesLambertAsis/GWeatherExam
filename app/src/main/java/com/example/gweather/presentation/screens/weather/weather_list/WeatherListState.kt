package com.example.gweather.presentation.screens.weather.weather_list

import com.example.gweather.domain.model.LocationDetails
import com.example.gweather.domain.model.WeatherDetails
import com.example.gweather.presentation.screens.weather.current_weather.CurrentWeatherState

sealed class WeatherListState {

    object Loading : WeatherListState()
    object Idle : WeatherListState()

    data class FetchingListSuccess(val weatherList: List<WeatherDetails>) : WeatherListState()

    data class FetchingListError(val msg: String) : WeatherListState()

}