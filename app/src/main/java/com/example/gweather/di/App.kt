package com.example.gweather.di

import androidx.room.Room
import com.example.gweather.BuildConfig
import com.example.gweather.data.local.AppDatabase
import com.example.gweather.data.remote.OpenWeatherApi
import com.example.gweather.data.repository.LoginRepositoryImpl
import com.example.gweather.data.repository.OpenWeatherRepositoryImpl
import com.example.gweather.domain.repository.LoginRepository
import com.example.gweather.domain.repository.OpenWeatherRepository
import com.example.gweather.domain.use_case.user.LoginUser
import com.example.gweather.domain.use_case.user.RegisterUser
import com.example.gweather.domain.use_case.user.UserUseCase
import com.example.gweather.domain.use_case.weather.GetCurrentWeather
import com.example.gweather.domain.use_case.weather.GetWeatherList
import com.example.gweather.domain.use_case.weather.OpenWeatherUseCase
import com.example.gweather.presentation.screens.login.LoginViewModel
import com.example.gweather.presentation.screens.weather.current_weather.WeatherViewModel
import com.example.gweather.presentation.screens.weather.weather_list.WeatherListScreen
import com.example.gweather.presentation.screens.weather.weather_list.WeatherListViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import kotlin.jvm.java

val app = module{

    val json = Json {
        ignoreUnknownKeys = true
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.WEATHER_API_BASE_URL)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(OpenWeatherApi::class.java)
    }

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "gweather_db"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    single {
        get<AppDatabase>().userDao()
    }
    single {
        get<AppDatabase>().weatherDao()
    }

    singleOf(::LoginRepositoryImpl).bind<LoginRepository>()
    singleOf(::OpenWeatherRepositoryImpl).bind<OpenWeatherRepository>()

    single { RegisterUser(get()) }
    single { LoginUser(get()) }
    singleOf(::UserUseCase)

    single { GetCurrentWeather(get()) }
    single { GetWeatherList(get()) }
    singleOf(::OpenWeatherUseCase)

    viewModelOf(::LoginViewModel)
    viewModelOf(::WeatherViewModel)
    viewModelOf(::WeatherListViewModel)
}