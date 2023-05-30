package com.myweatherapp.screens.favorites

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myweatherapp.model.Favorite
import com.myweatherapp.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: WeatherDBRepository) :
    ViewModel() {
    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())

    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged()
                .collect { listOfFav ->

                    if (listOfFav.isEmpty()) {

                    } else {
                        _favList.value = listOfFav

                    }
                }
        }
    }

    fun insertFavorite(favorite: Favorite) =
        viewModelScope.launch {
            repository.insertFavorite(favorite = favorite)
        }

    fun updateFavorite(favorite: Favorite) =
        viewModelScope.launch {
            repository.updateFavorite(favorite = favorite)
        }

    fun deleteFavorite(favorite: Favorite) =
        viewModelScope.launch {
            repository.deleteFavorite(favorite = favorite)
        }

    fun deleteAllFavorites() =
        viewModelScope.launch {
            repository.deleteAllFavorites()
        }

    fun getFavById(id: Int) =
        viewModelScope.launch {
            repository.getFavById(id)
        }

}