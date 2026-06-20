package com.example.gweather.presentation.screens.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gweather.presentation.screens.weather.current_weather.CurrentWeatherScreen
import com.example.gweather.presentation.screens.weather.weather_list.WeatherListScreen

@Composable
fun WeatherScreen() {

    var optionSelected by remember { mutableStateOf(Option.CURRENT_WEATHER) }

    Box(
        Modifier.fillMaxSize()
    ) {
        when(optionSelected){
            Option.CURRENT_WEATHER -> {
                CurrentWeatherScreen()
            }
            Option.WEATHER_LIST -> {
                WeatherListScreen()
            }
        }
        Row(
            Modifier
                .fillMaxWidth(.9f)
                .padding(bottom = 20.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                Modifier
                    .clickable(){
                        optionSelected = Option.CURRENT_WEATHER
                    }
                    .clip(RoundedCornerShape(5))
                    .background(if (optionSelected == Option.CURRENT_WEATHER) Color.White else Color.Transparent)
                    .border(
                        width = 2.dp,
                        color = if (optionSelected == Option.CURRENT_WEATHER) Color.Transparent else Color.White,
                        shape = RoundedCornerShape(5)
                        )
                    .weight(1f)
                    .fillMaxWidth(.9f),
                contentAlignment = Alignment.Center
            ) {
                Text("Current Weather",
                    color = if (optionSelected == Option.CURRENT_WEATHER) Color.Black else Color.White)
            }
            Spacer(Modifier.width(20.dp))
            Box(
                Modifier
                    .clickable(){
                        optionSelected = Option.WEATHER_LIST
                    }
                    .clip(RoundedCornerShape(5))
                    .background(if (optionSelected == Option.WEATHER_LIST) Color.White else Color.Transparent)
                    .border(
                        width = 2.dp,
                        color = if (optionSelected == Option.WEATHER_LIST) Color.Transparent else Color.White,
                        shape = RoundedCornerShape(5)
                    )
                    .weight(1f)
                    .fillMaxWidth(.9f),
                contentAlignment = Alignment.Center
            ) {
                Text("Current Weather",
                    color = if (optionSelected == Option.WEATHER_LIST) Color.Black else Color.White)
            }
        }
    }

}

enum class Option {
    CURRENT_WEATHER, WEATHER_LIST
}