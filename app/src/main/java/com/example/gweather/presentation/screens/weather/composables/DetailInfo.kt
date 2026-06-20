package com.example.gweather.presentation.screens.weather.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DetailInfo(
    label: String,
    data: String,
) {
    Row(
        Modifier.fillMaxWidth()
    ) {
        Text(text = "$label: $data", color = Color.Black)
    }
}