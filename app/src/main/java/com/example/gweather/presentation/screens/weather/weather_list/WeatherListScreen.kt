package com.example.gweather.presentation.screens.weather.weather_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.gweather.R
import com.example.gweather.presentation.screens.weather.current_weather.WeatherViewModel
import com.example.gweather.presentation.screens.weather.current_weather.CurrentWeatherState
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherListScreen(
    viewModel: WeatherListViewModel = koinViewModel()
) {


    val state by viewModel.state.collectAsStateWithLifecycle()

    val currentState = state

    LaunchedEffect(Unit) {
        viewModel.onEvent(
            event = WeatherListEvent.GetWeatherList
        )
    }

    Box(
        Modifier.fillMaxSize().background(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFF2196F3),
                    Color(0xFF0D47A1),
                    Color(0xFF1F31FF)
                )
            )
        ),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            Modifier
                .fillMaxHeight(.9f)
                .fillMaxWidth(.9f)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            when (currentState) {
                is WeatherListState.FetchingListError -> {
                    Text("Error: $currentState", color = Color.Black)
                }

                WeatherListState.Idle -> Unit
                WeatherListState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(20.dp)
                            .align(Alignment.Center),
                        color = Color(0xFF1F31FF),
                        trackColor = Color.Transparent
                    )
                }

                is WeatherListState.FetchingListSuccess -> {
                    Column() {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                modifier = Modifier.weight(.3f),
                                text = stringResource(R.string.id),
                                color = Color.Black
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = stringResource(R.string.time),
                                color = Color.Black
                            )
                            Text(
                                modifier = Modifier.weight(.5f),
                                text = stringResource(R.string.weather),
                                color = Color.Black
                            )
                        }
                        HorizontalDivider()
                        LazyColumn() {
                            items(
                                items = currentState.weatherList,
                                key = { weatherDetail -> weatherDetail.id }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        modifier = Modifier.weight(.3f),
                                        text = it.id,
                                        color = Color.Black
                                    )
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = it.time,
                                        color = Color.Black
                                    )
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = it.weather,
                                            color = Color.Black
                                        )
                                        AsyncImage(
                                            model = it.icon,
                                            contentDescription = "weather status icon",
                                            placeholder = painterResource(R.drawable.ic_launcher_foreground),
                                            error = painterResource(R.drawable.ic_launcher_foreground),
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }

                                }
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    }
}