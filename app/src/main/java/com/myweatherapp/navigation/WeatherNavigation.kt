package com.myweatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.myweatherapp.screens.about.AboutScreen
import com.myweatherapp.screens.favorites.FavoriteScreen
import com.myweatherapp.screens.favorites.FavoritesViewModel
import com.myweatherapp.screens.main.MainScreen
import com.myweatherapp.screens.main.viewModel.MainViewModel
import com.myweatherapp.screens.search.SearchScreen
import com.myweatherapp.screens.settings.SettingsScreen
import com.myweatherapp.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        //where all the screens should be hosted
        composable(route = WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable(
            route = "$route/{city}",
            arguments = listOf(navArgument(name = "city") {
                type = NavType.StringType
            })
        ) { response ->
            response.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                val favoritesViewModel = hiltViewModel<FavoritesViewModel>()
                MainScreen(navController, mainViewModel, city, favoritesViewModel)
            }
        }
        composable(route = WeatherScreens.SearchScreen.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            SearchScreen(navController, mainViewModel)
        }

        composable(route = WeatherScreens.AboutScreen.name) {
            AboutScreen(navController)
        }
        composable(route = WeatherScreens.FavoriteScreen.name) {
            FavoriteScreen(navController)
        }
        composable(route = WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController)
        }
    }
}