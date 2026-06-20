package com.example.gweather.data.remote

import com.example.gweather.BuildConfig
import com.example.gweather.data.remote.dto.OpenWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("/data/2.5/weather")
    suspend fun getOpenWeather (
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = BuildConfig.OPENWEATHER_API_KEY,
    ): OpenWeatherResponse

}