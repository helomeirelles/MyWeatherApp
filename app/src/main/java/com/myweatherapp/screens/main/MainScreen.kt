package com.myweatherapp.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.myweatherapp.data.DataOrException
import com.myweatherapp.model.Weather
import com.myweatherapp.navigation.WeatherScreens
import com.myweatherapp.screens.favorites.FavoritesViewModel
import com.myweatherapp.screens.main.viewModel.MainViewModel
import com.myweatherapp.screens.settings.SettingsViewModel
import com.myweatherapp.utils.*
import com.myweatherapp.widgets.*

@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String?,
    favoritesViewModel: FavoritesViewModel,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    val curCity: String = if(city?.isBlank() == true) "Taubaté" else city.toString()

    val unitFromDB = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }

    var isImperial by remember {
        mutableStateOf(false)
    }

    if(!unitFromDB.isNullOrEmpty()){
        unit = unitFromDB[0].unit
        isImperial = unit == "imperial"
        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeatherData(city.toString(), units = unit)
        }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp), color = AppColors.appBlue)
        } else if (weatherData.data != null) {
            MainScaffold(weather = weatherData.data, navController = navController, favoritesViewModel, isImperial)
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScaffold(
    weather: Weather?,
    navController: NavController,
    favoritesViewModel: FavoritesViewModel,
    isImperial: Boolean
) {
    Scaffold(topBar = {
        favoritesViewModel.getFavById(weather?.city?.id ?: 0)
        WeatherAppBar(
            title = "${weather?.city?.name}, ${weather?.city?.country}",
            cityId = weather?.city?.id ?: 0,
            navController = navController,
            onAddActionClicked = {navController.navigate(WeatherScreens.SearchScreen.name)}
        )
    }, modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
        MainContent(data = weather, isImperial = isImperial)
    }
}

@Composable
fun MainContent(data: Weather?, isImperial: Boolean) {
    val currentDate = data?.list?.first()?.dt?.let { formatDate(it) }
    val weatherItem = data?.list?.first()
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = currentDate.toString(),
            style = MaterialTheme.typography.caption,
            color = AppColors.darkGray,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(6.dp)
        )
        RoundedCircleComponent(weatherItem)
        weatherItem?.let { PrecipitationComponent(it, isImperial) }
        Divider(color = AppColors.lightGray, thickness = 1.dp)
        SunriseSunset(weatherItem)
        WeatherForecast(data)
    }
}





