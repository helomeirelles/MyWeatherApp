package com.myweatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myweatherapp.model.Favorite
import com.myweatherapp.model.Settings

@Database(entities = [Favorite::class, Settings::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}