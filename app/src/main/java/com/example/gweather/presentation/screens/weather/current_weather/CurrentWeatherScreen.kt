package com.example.gweather.presentation.screens.weather.current_weather

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresPermission
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.gweather.R
import com.example.gweather.domain.model.LocationDetails
import com.example.gweather.presentation.screens.weather.composables.DetailInfo
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.androidx.compose.koinViewModel

@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
@Composable
fun CurrentWeatherScreen(
    viewModel: WeatherViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    val currentState = state

    val client = LocationServices.getFusedLocationProviderClient(context)
    var locationDetails by remember { mutableStateOf(LocationDetails()) }
    val scope = rememberCoroutineScope()
    val getWeather = suspend {
        val location = client.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).await()
        if (location != null) {
            viewModel.onEvent(
                CurrentWeatherEvent.GetCurrentCurrentWeather(
                    location.latitude, location.longitude
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        getWeather()
    }

    BackHandler {}
    Box(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF2196F3),
                        Color(0xFF0D47A1),
                        Color(0xFF1F31FF)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        when (currentState) {
            CurrentWeatherState.Idle -> Unit
            CurrentWeatherState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10))
                        .background(Color.White)
                        .padding(20.dp)
                        .align(Alignment.Center),
                    color = Color(0xFF1F31FF),
                    trackColor = Color.Transparent
                )
            }
            is CurrentWeatherState.FetchDetailsSuccess -> {
                locationDetails = currentState.locationDetails

                Column(
                    Modifier
                        .fillMaxWidth(.9f)
                        .clip(RoundedCornerShape(5))
                        .background(Color.White)
                        .padding(horizontal = 6.dp, vertical = 10.dp)
                ) {
                    DetailInfo(
                        label = stringResource(R.string.location),
                        data = "${locationDetails.country}, ${locationDetails.city}"
                    )
                    DetailInfo(
                        label = stringResource(R.string.temperature),
                        data = locationDetails.temp
                    )
                    DetailInfo(
                        label = stringResource(R.string.sunrise),
                        data = locationDetails.sunrise
                    )
                    DetailInfo(
                        label = stringResource(R.string.sunset),
                        data = locationDetails.sunset
                    )
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.weather) + ": ",
                            color = Color.Black
                        )
                        Text(
                            text = locationDetails.currentWeather,
                            color = Color.Black
                        )
                        AsyncImage(
                            model = locationDetails.icon,
                            contentDescription = "weather status icon",
                            placeholder = painterResource(R.drawable.ic_launcher_foreground),
                            error = painterResource(R.drawable.ic_launcher_foreground),
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }

            }

            is CurrentWeatherState.FetchDetailsError -> {
                Column(
                    Modifier
                        .fillMaxWidth(.9f)
                        .clip(RoundedCornerShape(10))
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(currentState.msg, modifier = Modifier.padding(10.dp), color = Color.Black)
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "Refresh",
                        modifier = Modifier
                            .clickable{
                                scope.launch {
                                    getWeather()
                                }
                            }
                            .clip(RoundedCornerShape(10))
                            .background(Color(0xFF0D47A1))
                            .padding(10.dp)
                    )
                }
            }
        }
    }
}