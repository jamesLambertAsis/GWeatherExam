package com.example.gweather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gweather.data.local.dao.UserDao
import com.example.gweather.data.local.dao.WeatherDao
import com.example.gweather.data.local.entity.UserEntity
import com.example.gweather.data.local.entity.WeatherEntity

@Database(entities = [UserEntity::class, WeatherEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun weatherDao(): WeatherDao
}