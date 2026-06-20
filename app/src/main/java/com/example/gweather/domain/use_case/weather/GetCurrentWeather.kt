package com.example.gweather.domain.use_case.weather

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.gweather.BuildConfig
import com.example.gweather.core.TaskResult
import com.example.gweather.domain.model.LocationDetails
import com.example.gweather.domain.repository.OpenWeatherRepository
import retrofit2.HttpException
import java.time.format.DateTimeFormatter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale

class GetCurrentWeather(
    private val repository: OpenWeatherRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(lat: Double, lon: Double): TaskResult<LocationDetails> {
        try {
            val result = repository.getWeather(lat, lon)
            val tempInCelsius = result.main.temp - 273.15
            val formattedTemp = "%.1f°C".format(tempInCelsius)
            val currentTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("MM/dd/yy HH:mm a"))
            val iconUrl = BuildConfig.WEATHER_ICON_URL.format(result.iconCode)
            repository.saveWeather(
                weather = result.weather[0].description,
                time = currentTime,
                icon = iconUrl
            )
            return TaskResult.Success(
                LocationDetails(
                    country = result.sys.country,
                    city = result.name,
                    sunrise = formatUnixTo12Hr(result.sys.sunrise),
                    sunset = formatUnixTo12Hr(result.sys.sunset),
                    temp = formattedTemp,
                    currentWeather = result.weather[0].description,
                    icon = iconUrl
                )
            )
        } catch (e: Exception) {
            return TaskResult.Error(errorMessage = e.message ?: "Unknown error")
        } catch (e: HttpException) {
            return TaskResult.Error(errorMessage = e.message ?: "Unknown error")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun formatUnixTo12Hr(unixTime: Long): String {
    val instant = Instant.ofEpochSecond(unixTime)
    val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
        .withZone(ZoneId.systemDefault()) //device local time zone
    return formatter.format(instant)
}