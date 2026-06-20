package com.example.gweather.presentation.screens.weather.current_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gweather.core.TaskResult
import com.example.gweather.domain.model.LocationDetails
import com.example.gweather.domain.use_case.weather.OpenWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherUseCase: OpenWeatherUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CurrentWeatherState>(CurrentWeatherState.Loading)

    val state = _state.asStateFlow()
    fun onEvent(event: CurrentWeatherEvent) {
        when(event){
            is CurrentWeatherEvent.GetCurrentCurrentWeather -> {
                if (_state.value is CurrentWeatherState.FetchDetailsSuccess) return
                _state.value = CurrentWeatherState.Loading
                viewModelScope.launch {
                    val result = weatherUseCase.getWeather(event.lat, event.lon)
                    if (result is TaskResult.Success) {
                        _state.value = CurrentWeatherState.FetchDetailsSuccess(result.data)
                    } else if (result is TaskResult.Error) {
                        _state.value = CurrentWeatherState.FetchDetailsError(result.errorMessage)
                    }
                }
            }
        }

    }

}