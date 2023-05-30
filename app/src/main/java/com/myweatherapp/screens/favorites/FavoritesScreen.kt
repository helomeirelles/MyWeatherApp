package com.myweatherapp.screens.favorites

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.myweatherapp.model.Favorite
import com.myweatherapp.navigation.WeatherScreens
import com.myweatherapp.screens.settings.SettingsViewModel
import com.myweatherapp.utils.AppColors
import com.myweatherapp.widgets.WeatherAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(
    navController: NavHostController,
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    Scaffold(topBar = {
        WeatherAppBar(
            title = "Favorite Cities",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            isMainScreen = false
        ) {
            navController.popBackStack()
        }
    }, modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = favoritesViewModel.favList.collectAsState().value
                val units = if(settingsViewModel.unitList.collectAsState().value.isEmpty()) "metrics" else settingsViewModel.unitList.collectAsState().value[0].toString()

                LazyColumn {
                    items(list) {
                        CityRow(it, navController, favoritesViewModel, units)
                    }
                }
            }
        }
    }
}

@Composable
fun CityRow(
    favorite: Favorite,
    navController: NavHostController,
    favoritesViewModel: FavoritesViewModel,
    units: String
) {
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(WeatherScreens.MainScreen.name + "/${favorite.city}")
            }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Color.Transparent)
                .border(
                    BorderStroke(2.dp, color = AppColors.offWhite),
                    shape = RoundedCornerShape(34.dp)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier.padding(16.dp),
                text = favorite.city,
                style = TextStyle(color = AppColors.darkGray)
            )
            Text(
                modifier = Modifier
                    .background(shape = CircleShape, color = AppColors.offWhite)
                    .padding(start = 8.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
                text = favorite.country,
                style = TextStyle(color = AppColors.darkGray),
                textAlign = TextAlign.Center
            )
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        favoritesViewModel.deleteFavorite(
                            favorite
                        )
                    },
                imageVector = Icons.Default.Delete,
                tint = AppColors.darkGray,
                contentDescription = "delete icon"
            )
        }
    }

}
