package com.myweatherapp.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.myweatherapp.navigation.WeatherScreens
import com.myweatherapp.utils.AppColors
import kotlinx.coroutines.delay

@Composable
fun WeatherSplashScreen(navController: NavHostController) {
    val defaultCity = "Taubate"
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(targetValue = 1f, animationSpec = tween(durationMillis = 800, easing = {
            OvershootInterpolator(100f).getInterpolation(it)
        }))
        delay(2000)
        navController.navigate(WeatherScreens.MainScreen.name + "/$defaultCity")
    })
    Surface(modifier = Modifier.fillMaxSize()
        .scale(scale.value)) {
        val gradient = Brush.linearGradient(listOf(AppColors.lightBlue, AppColors.lightYellow))
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradient), color = Color.Transparent
        ) {
            Column(
                modifier = Modifier.fillMaxSize().background(Color.Transparent),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(

                    contentScale = ContentScale.Fit,
                    painter = painterResource(id = com.myweatherapp.R.drawable.ic_cloudy),
                    contentDescription = ""
                )

            }
        }
    }
}