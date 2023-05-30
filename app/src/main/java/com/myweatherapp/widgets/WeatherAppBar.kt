package com.myweatherapp.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.myweatherapp.R
import com.myweatherapp.data.DropDownMenuOptions
import com.myweatherapp.model.Favorite
import com.myweatherapp.navigation.WeatherScreens
import com.myweatherapp.screens.favorites.FavoritesViewModel
import com.myweatherapp.utils.AppColors

@Composable
fun WeatherAppBar(
    title: String = "Title",
    cityId: Int = 0,
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    if (showDialog.value) {
        ShowSettingDropDownMenu(
            showDialog, navController
        )
    }

    TopAppBar(
        modifier = Modifier.border(
            border = BorderStroke(
                2.dp,
                color = if (isMainScreen) AppColors.offWhite else Color.Transparent
            ),
            shape = RoundedCornerShape(25.dp)
        ),
        title = {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.darkGray,
                    textAlign = TextAlign.Center
                )
            )
        },
        actions = {
            if (isMainScreen) {

                IconButton(onClick = { onAddActionClicked.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search icon",
                        tint = AppColors.darkGray
                    )
                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "more icon",
                        tint = AppColors.darkGray
                    )
                }

            } else {
                Box() {}
            }
        },
        navigationIcon = {

            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    tint = AppColors.darkGray,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable { onButtonClicked.invoke() })
            }

            if (isMainScreen) {

                val isAlreadyFavList =
                    favoritesViewModel.favList.collectAsState().value.filter { item ->
                        (item.city == title.split(",").toTypedArray()[0])
                    }

                val checkAgain = remember {
                    mutableStateOf(isAlreadyFavList.isEmpty())
                }

                val favoriteIcon = remember {
                    mutableStateOf(Icons.Default.FavoriteBorder)
                }

                favoriteIcon.value = if (checkAgain.value) {
                    Icons.Default.FavoriteBorder
                } else {
                    Icons.Default.Favorite
                }

                val data = title.split(",").toTypedArray()

                Icon(
                    imageVector = favoriteIcon.value,
                    contentDescription = "Favorite icon",
                    modifier = Modifier
                        .scale(0.9f)
                        .padding(start = 8.dp)
                        .background(color = Color.Transparent)
                        .clickable {
                            if (checkAgain.value) {
                                favoritesViewModel.insertFavorite(
                                    Favorite(
                                        id = cityId,
                                        city = data[0],
                                        country = data[1]
                                    )
                                )
                                favoriteIcon.value = Icons.Default.Favorite
                                checkAgain.value = false
                                Toast
                                    .makeText(
                                        context,
                                        context.getString(R.string.city_added),
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            } else {
                                favoritesViewModel.deleteFavorite(
                                    Favorite(
                                        id = cityId, city = data[0],
                                        country = data[1]
                                    )
                                )
                                checkAgain.value = true
                                favoriteIcon.value = Icons.Default.FavoriteBorder
                                Toast
                                    .makeText(
                                        context,
                                        context.getString(R.string.city_removed),
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }

                        },
                    tint = AppColors.softRed
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation
    )
}

@Composable
fun ShowToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(modifier = Modifier
            .width(140.dp)
            .background(Color.White), expanded = expanded, onDismissRequest = {

        }) {
            DropDownMenuOptions.values().forEachIndexed { _, text ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                }) {
                    Icon(
                        imageVector = when (text) {
                            DropDownMenuOptions.About -> Icons.Default.Info
                            DropDownMenuOptions.Favorites -> Icons.Default.Favorite
                            DropDownMenuOptions.Settings -> Icons.Default.Settings
                        }, contentDescription = "",
                        tint = AppColors.darkGray
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .clickable {
                                navController.navigate(
                                    route = when (text) {
                                        DropDownMenuOptions.About -> WeatherScreens.AboutScreen.name
                                        DropDownMenuOptions.Favorites -> WeatherScreens.FavoriteScreen.name
                                        DropDownMenuOptions.Settings -> WeatherScreens.SettingsScreen.name
                                    }
                                )
                            },
                        text = text.name,
                        style = TextStyle(color = AppColors.darkGray)
                    )
                }
            }
        }
    }
}

private fun manageFavoriteButton(
    isAlreadyFav: Boolean,
    data: String,
    favoritesViewModel: FavoritesViewModel,
    cityId: Int
): Boolean {

    val currentFavorite = Favorite(
        cityId,
        data[CompanionClass.CITY_INDEX].toString(),
        data[CompanionClass.COUNTRY_INDEX].toString()
    )
    if (!isAlreadyFav) {
        favoritesViewModel.insertFavorite(currentFavorite)
        return true
    } else {
        favoritesViewModel.deleteFavorite(currentFavorite)
        return false
    }

}

class CompanionClass {
    companion object {
        const val CITY_INDEX = 0
        const val COUNTRY_INDEX = 1
    }
}
