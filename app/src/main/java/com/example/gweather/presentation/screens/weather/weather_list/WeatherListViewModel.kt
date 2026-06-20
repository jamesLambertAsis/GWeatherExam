package com.example.gweather.presentation.screens.weather.weather_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gweather.core.TaskResult
import com.example.gweather.domain.model.LocationDetails
import com.example.gweather.domain.model.WeatherDetails
import com.example.gweather.domain.use_case.weather.OpenWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherListViewModel(
private val weatherUseCase: OpenWeatherUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<WeatherListState>(WeatherListState.Loading)

    val state = _state.asStateFlow()

    fun onEvent(event: WeatherListEvent) {
        when(event){
            WeatherListEvent.GetWeatherList -> {
                _state.value = WeatherListState.Loading
                viewModelScope.launch {
                    weatherUseCase.getWeatherList().collect { result ->
                        when (result) {

                            TaskResult.Loading -> _state.value = WeatherListState.Loading

                            is TaskResult.Error<*> -> _state.value =
                                WeatherListState.FetchingListError(result.errorMessage)

                            is TaskResult.Success<List<WeatherDetails>> -> _state.value =
                                WeatherListState.FetchingListSuccess(result.data.sortedBy { it.id.toInt() })

                        }
                    }
                }
            }
        }
    }

}