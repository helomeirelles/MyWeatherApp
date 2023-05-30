package com.myweatherapp.network

import com.myweatherapp.model.Weather
import com.myweatherapp.model.WeatherItem
import com.myweatherapp.model.WeatherObject
import com.myweatherapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApiImp {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String = "imperial",
        @Query("cnt") countOfDays: Int = 7,
        @Query("appid") appid: String = Constants.API_KEY


    ): Weather

}