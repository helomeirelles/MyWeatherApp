package com.myweatherapp.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myweatherapp.model.Favorite
import com.myweatherapp.model.Settings
import com.myweatherapp.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDBRepository): ViewModel() {

    private val _unitList = MutableStateFlow<List<Settings>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSettings().distinctUntilChanged()
                .collect { listOfUnits ->
                    if (listOfUnits.isEmpty()) {

                    } else {

                        _unitList.value = listOfUnits
                    }
                }
        }
    }

    fun insertSettings(unit: Settings) =
        viewModelScope.launch {
            repository.insertSettings(unit)
        }

    fun updateSettings(unit: Settings) =
        viewModelScope.launch {
            repository.updateSettings(unit)
        }

    fun deleteSettings(unit: Settings) =
        viewModelScope.launch {
            repository.deleteSettings(unit)
        }

    fun deleteAllSettings() =
        viewModelScope.launch {
            repository.deleteAllSettings()
        }



}