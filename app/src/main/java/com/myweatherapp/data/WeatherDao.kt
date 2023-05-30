package com.myweatherapp.data

import androidx.room.*
import com.myweatherapp.model.Favorite
import com.myweatherapp.model.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    // FAVORITE TABLE

    @Query(value = "SELECT * from fav_tbl")
    fun getFavorites(): Flow<List<Favorite>>

    @Query(value = "SELECT * from fav_tbl where id = :id")
    suspend fun getFavById(id: Int): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE from fav_tbl")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    // UNIT TABLE

    @Query("SELECT * from settings_tbl")
    fun getSettings(): Flow<List<Settings>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(unit: Settings)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSettings(unit: Settings)

    @Query("DELETE from settings_tbl")
    suspend fun deleteAllSettings()

    @Delete
    suspend fun deleteSettings(unit: Settings)
}