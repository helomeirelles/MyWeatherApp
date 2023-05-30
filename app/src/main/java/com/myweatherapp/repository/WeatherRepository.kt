package com.myweatherapp.repository

import com.myweatherapp.data.DataOrException
import com.myweatherapp.model.Weather
import com.myweatherapp.network.WeatherApiImp
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApiImp){
    suspend fun getWeather(city: String, units: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(city = city, units = units)

        } catch (e: Exception){
            return DataOrException(e = e)

        }
        return DataOrException(data = response)
    }

}