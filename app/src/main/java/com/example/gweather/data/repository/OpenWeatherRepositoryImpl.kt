package com.example.gweather.data.repository

import com.example.gweather.data.local.dao.WeatherDao
import com.example.gweather.data.local.entity.WeatherEntity
import com.example.gweather.data.remote.OpenWeatherApi
import com.example.gweather.data.remote.dto.OpenWeatherResponse
import com.example.gweather.domain.model.WeatherDetails
import com.example.gweather.domain.repository.OpenWeatherRepository

class OpenWeatherRepositoryImpl(
    private val api: OpenWeatherApi,
    private val dao: WeatherDao
): OpenWeatherRepository {

    override suspend fun getWeather(
        lat: Double,
        lon: Double
    ): OpenWeatherResponse {
        return api.getOpenWeather(lat, lon)
    }

    override suspend fun saveWeather(
        weather: String,
        time: String,
        icon: String
    ) {
        dao.insertWeather(
            WeatherEntity(
                weather = weather,
                time = time,
                icon = icon
            )
        )
    }

    override suspend fun getWeathers(): List<WeatherDetails> {
        return dao.getWeather().map {
            WeatherDetails(
                id = it.id.toString(),
                weather = it.weather,
                time = it.time,
                icon = it.icon
            )
        }
    }

}