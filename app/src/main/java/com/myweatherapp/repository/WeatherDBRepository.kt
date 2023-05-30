package com.myweatherapp.repository

import com.myweatherapp.data.WeatherDao
import com.myweatherapp.model.Favorite
import com.myweatherapp.model.Settings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDBRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()

    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite = favorite)

    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite = favorite)

    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()

    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite = favorite)

    suspend fun getFavById(id: Int) = weatherDao.getFavById(id = id)

    fun getSettings(): Flow<List<Settings>> = weatherDao.getSettings()

    suspend fun insertSettings(unit: Settings) = weatherDao.insertSettings(unit)

    suspend fun updateSettings(unit: Settings) = weatherDao.updateSettings(unit)

    suspend fun deleteAllSettings() = weatherDao.deleteAllSettings()

    suspend fun deleteSettings(unit: Settings) = weatherDao.deleteSettings(unit)
}