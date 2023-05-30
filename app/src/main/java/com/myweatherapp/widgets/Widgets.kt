package com.myweatherapp.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.myweatherapp.R
import com.myweatherapp.model.Weather
import com.myweatherapp.model.WeatherItem
import com.myweatherapp.utils.AppColors
import com.myweatherapp.utils.formatDate
import com.myweatherapp.utils.formatDateTime
import com.myweatherapp.utils.formatDecimals


@Composable
fun WeatherForecast(weather: Weather?) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "This Week",
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.darkGray
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(modifier = Modifier
            .background(AppColors.offWhite, shape = RoundedCornerShape(16.dp))
            .padding(4.dp), content = {
            weather?.list?.forEachIndexed { index, weatherItem ->
                item {
                    val annotatedString = buildAnnotatedString {

                        withStyle(
                            SpanStyle(
                                color = AppColors.appBlue,
                                fontSize = 17.sp
                            )
                        ) {
                            append("${formatDecimals(weatherItem.temp.max)}°")
                        }

                        withStyle(
                            SpanStyle(
                                color = AppColors.lightGray,
                                fontSize = 17.sp
                            )
                        ) {
                            append("${formatDecimals(weatherItem.temp.min)}°")
                        }

                    }
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp), shape = RoundedCornerShape(
                            20
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = formatDate(weather.list[index].dt).split(",")[0],
                                modifier = Modifier.padding(8.dp),
                                style = TextStyle(fontSize = 17.sp, color = AppColors.darkGray)
                            )

                            WeatherStateImage(
                                getImageUrl(weatherItem.weather.first().icon),
                                40.dp,
                                Modifier.padding(start = 4.dp)
                            )

                            Text(
                                text = weatherItem.weather.first().description,
                                modifier = Modifier
                                    .background(
                                        color = AppColors.mediumYellow,
                                        shape = RoundedCornerShape(34.dp)
                                    )
                                    .padding(8.dp)
                            )
                            Text(
                                text = annotatedString,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = AppColors.darkBlue,
                                    fontSize = 17.sp
                                ),
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }

                    }
                    Spacer(modifier = Modifier.size(4.dp))
                }
            }

        })
    }
}

private fun getImageUrl(iconId: String): String {
    return "https://openweathermap.org/img/wn/$iconId.png"
}


@Composable
fun WeatherStateImage(imageUrl: String, size: Dp = 70.dp, modifier: Modifier = Modifier) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = "",
        modifier = modifier.size(size)
    )
}

@Composable
fun SunriseSunset(weatherItem: WeatherItem?) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 4.dp),
                painter = painterResource(id = R.drawable.ic_sunrise),
                contentDescription = "sunrise icon"
            )
            Text(
                text = formatDateTime(weatherItem?.sunrise),
                style = MaterialTheme.typography.caption
            )
        }
        Row(modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = formatDateTime(weatherItem?.sunset),
                style = MaterialTheme.typography.caption
            )
            Image(
                modifier = Modifier
                    .size(20.dp)
                    .padding(start = 4.dp),
                painter = painterResource(id = R.drawable.ic_sunset),
                contentDescription = "sunset icon"
            )
        }
    }
}


@Composable
fun PrecipitationComponent(weather: WeatherItem, isImperial: Boolean = false) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 4.dp),
                painter = painterResource(id = R.drawable.ic_humidity),
                contentDescription = "humidity icon",
                tint = AppColors.mediumBlue
            )
            Text(text = "${weather.humidity}%", style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 4.dp),
                painter = painterResource(id = R.drawable.ic_pressure),
                contentDescription = "pressure icon",
                tint = AppColors.mediumBlue
            )
            Text(text = "${weather.pressure} psi", style = MaterialTheme.typography.caption)
        }


        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 4.dp),
                painter = painterResource(id = R.drawable.ic_wind),
                contentDescription = "wind icon",
                tint = AppColors.mediumBlue
            )
            Text(text = "${weather.speed} ${if(isImperial) "mph" else "m/s"}", style = MaterialTheme.typography.caption)
        }


    }
}


@Composable
fun RoundedCircleComponent(weatherItem: WeatherItem?) {

    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem?.weather?.first()?.icon}.png"
    val currentTemperature = weatherItem?.temp?.day?.let { formatDecimals(it) }
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), shape = RoundedCornerShape(34.dp), color = Color.White, border = BorderStroke(1.dp, color = AppColors.offWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            WeatherStateImage(imageUrl = imageUrl)
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "${currentTemperature.toString()}°",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.darkGray
                    )
                )
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = "${weatherItem?.weather?.first()?.main}",
                    style = TextStyle(fontSize = 14.sp, color = AppColors.darkGray)
                )

            }
        }


    }
}
