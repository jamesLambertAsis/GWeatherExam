package com.example.gweather

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gweather.presentation.navigation.AppNavigationItem
import com.example.gweather.presentation.screens.login.LoginScreen
import com.example.gweather.presentation.screens.weather.WeatherScreen
import com.example.gweather.presentation.screens.weather.current_weather.CurrentWeatherScreen
import com.example.gweather.ui.theme.GWeatherTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GWeatherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        Modifier.fillMaxSize()
                    ) {
                        val navController = rememberNavController()
                        NavHost(
                            modifier = Modifier
                                .imePadding()
                                .navigationBarsPadding()
                                .statusBarsPadding(),
                            navController = navController,
                            startDestination = AppNavigationItem.LoginScreen
                        ) {
                            composable<AppNavigationItem.LoginScreen> {
                                LoginScreen() {
                                    navController.navigate(AppNavigationItem.WeatherScreen)
                                }
                            }
                            composable<AppNavigationItem.WeatherScreen> {
                                WeatherScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

