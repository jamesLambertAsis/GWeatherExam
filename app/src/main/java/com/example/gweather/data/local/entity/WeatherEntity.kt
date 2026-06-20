package com.example.gweather.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Weather")

data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val weather: String,
    val time: String,
    val icon: String
)