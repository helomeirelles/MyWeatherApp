package com.myweatherapp.screens.main.viewModel

import androidx.lifecycle.ViewModel
import com.myweatherapp.data.DataOrException
import com.myweatherapp.model.Weather
import com.myweatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    suspend fun getWeatherData(city: String, units: String): DataOrException<Weather, Boolean, java.lang.Exception> {
        return repository.getWeather(city = city, units = units)
    }
}

