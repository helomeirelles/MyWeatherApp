package com.myweatherapp.screens.about

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myweatherapp.R
import com.myweatherapp.utils.AppColors
import com.myweatherapp.widgets.WeatherAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AboutScreen(navController: NavHostController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "About",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            isMainScreen = false
        ) {
            navController.popBackStack()
        }
    }){
        Components()
    }
}

@Preview
@Composable
fun Components(){

    val uriHandler = LocalUriHandler.current
    val openWeatherUrlString = stringResource(id = R.string.open_weather_api_url)
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    shape = RoundedCornerShape(34.dp),
        border = BorderStroke(1.dp, AppColors.lightGray)
    ) {
        Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.ic_cloudy), contentDescription = "app icon")
            Text(text = stringResource(id = R.string.about_message),
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            style = TextStyle(lineHeight = 24.sp, color = AppColors.darkGray)
            )
            Button(onClick = { uriHandler.openUri(openWeatherUrlString) },
            modifier = Modifier
                .background(Color.Transparent)
                .padding(8.dp),
            border = BorderStroke(1.dp, color = AppColors.lightGreen),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp),
                shape = RoundedCornerShape(34.dp)
            ) {
                Text(text = stringResource(id = R.string.learn_more),
                modifier = Modifier
                    .fillMaxWidth(),
                color = AppColors.green,
                textAlign = TextAlign.Center)
            }

        }

    }
}