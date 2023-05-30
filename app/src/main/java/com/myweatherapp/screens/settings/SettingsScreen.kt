package com.myweatherapp.screens.settings

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.myweatherapp.R
import com.myweatherapp.model.Settings
import com.myweatherapp.utils.AppColors
import com.myweatherapp.widgets.WeatherAppBar


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    val measurementUnits = listOf("imperial", "metric")

    val choiceFromDB = settingsViewModel.unitList.collectAsState().value

    val defaultChoice = if(choiceFromDB.isNullOrEmpty()) measurementUnits[0] else choiceFromDB[0].unit
    var choiceState by remember(defaultChoice) {
        mutableStateOf(defaultChoice)
    }
    var unitToggleState by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Scaffold(topBar = {
        WeatherAppBar(
            title = "Settings",
            icon = Icons.Default.ArrowBack,
            isMainScreen = false,
            navController = navController
        ) {
            navController.popBackStack()
        }
    }) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change Units of Measurement",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                IconToggleButton(
                    checked = !unitToggleState, onCheckedChange = {
                        unitToggleState = !it
                        if(choiceState == "imperial") choiceState = "metric" else choiceState = "imperial"

                    }, modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(5.dp)
                        .background(color = AppColors.green, shape = RoundedCornerShape(16.dp))
                ) {
                    Text(text = if (choiceState == "imperial") "Fahrenheit °F" else "Celsius °C")
                }
                Button(
                    onClick = {
                        settingsViewModel.deleteAllSettings()
                        settingsViewModel.insertSettings(Settings(unit = choiceState))
                        val message = context.getString(R.string.unit_saved) + if(choiceState == "imperial") "Fahrenheit °F" else "Celsius °C"
                        Toast
                            .makeText(
                                context,
                                message,
                                Toast.LENGTH_SHORT
                            )
                            .show()
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(0.3f),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, AppColors.lightGray)
                ) {
                    Text(
                        text = stringResource(id = R.string.save_label),
                        style = TextStyle(
                            color = AppColors.darkGray,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun test() {

}