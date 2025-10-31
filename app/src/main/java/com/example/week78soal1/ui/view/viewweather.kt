package com.example.week78soal1.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.week78soal1.R
import com.example.week78soal1.ui.model.WeatherModel
import com.example.week78soal1.ui.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun viewWeather(viewModel: WeatherViewModel = viewModel()) {
    val uiState by viewModel.state.collectAsState()
    val searchQuery by viewModel.searchText.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.updateSearchText(it) },
                    modifier = Modifier
                        .weight(1f)
                        .height(53.dp),
                    placeholder = {
                        Text(
                            "Search",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = Color.White.copy(alpha = 0.6f),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(15.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.15f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.15f),
                        focusedBorderColor = Color.White.copy(alpha = 0.3f),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Button(
                    onClick = { viewModel.loadWeather(searchQuery) },
                    modifier = Modifier.height(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.15f)
                    ),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                    Text(
                        text = "Search",
                        color = Color.White,
                        fontSize = 14.sp,
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    when {
                        uiState.isLoading -> {
                            LoadingState()
                        }

                        uiState.hasError -> {
                            ErrorState("City not found or network error")
                        }

                        uiState.weatherData != null -> {
                            WeatherContent(uiState.weatherData!!)
                        }

                        else -> {
                            InitialState()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InitialState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            modifier = Modifier.size(80.dp),
            tint = Color.White.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Search for a city to get started",
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LoadingState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Loading...",
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun ErrorState(message: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 150.dp, start = 32.dp, end = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = android.R.drawable.stat_notify_error),
            contentDescription = "Error",
            modifier = Modifier.size(80.dp),
            tint = Color.Red.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Oops! Something went wrong",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun WeatherContent(weather: WeatherModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = weather.cityName,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        val currentDate = SimpleDateFormat("MMMM dd", Locale.getDefault()).format(Date())
        Text(
            text = currentDate,
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(5.dp))

        val updateTime = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
        Text(
            text = "Updated as of $updateTime",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(75.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = weather.weatherIcon,
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(80.dp),
                    colorFilter = if (weather.weatherCondition.equals("Clear", ignoreCase = true))
                        ColorFilter.tint(Color(0xFFda7454))
                    else
                        null
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = weather.weatherCondition,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "${weather.temp}°C",
                    color = Color.White,
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-3).sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            val pandaImage = when (weather.weatherCondition.lowercase()) {
                "clear" -> R.drawable.clearpanda
                "clouds" -> R.drawable.cloudypanda
                "rain" -> R.drawable.rainpanda
                else -> R.drawable.clearpanda
            }
            Image(
                painter = painterResource(id = pandaImage),
                contentDescription = "Panda",
                modifier = Modifier.size(150.dp)
            )
        }


        Spacer(modifier = Modifier.height(100.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                WeatherDetailCard(
                    icon = R.drawable.humidity,
                    label = "HUMIDITY",
                    value = "${weather.humidityLevel}%",
                    modifier = Modifier.weight(1f)
                )
                WeatherDetailCard(
                    icon = R.drawable.wind,
                    label = "WIND",
                    value = "${weather.windVelocity}km/h",
                    modifier = Modifier.weight(1f)
                )
                WeatherDetailCard(
                    icon = R.drawable.feelslike,
                    label = "FEELS LIKE",
                    value = "${weather.tempFeelsLike}°",
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                WeatherDetailCard(
                    icon = R.drawable.rainfall,
                    label = "RAINFALL",
                    value = "${String.format("%.1f", weather.rainAmount)}mm",
                    modifier = Modifier.weight(1f)
                )
                WeatherDetailCard(
                    icon = R.drawable.pressure,
                    label = "PRESSURE",
                    value = "${weather.pressureLevel}hPa",
                    modifier = Modifier.weight(1f)
                )
                WeatherDetailCard(
                    icon = R.drawable.cloud,
                    label = "CLOUDS",
                    value = "${weather.cloudCoverage}%",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val sunriseTime = SimpleDateFormat("h:mm a", Locale.getDefault())
                .format(Date(weather.sunriseTime * 1000L))
            SunCard(
                icon = R.drawable.sunrise,
                label = "SUNRISE",
                value = sunriseTime,
                modifier = Modifier.weight(1f)
            )

            val sunsetTime = SimpleDateFormat("h:mm a", Locale.getDefault())
                .format(Date(weather.sunsetTime * 1000L))
            SunCard(
                icon = R.drawable.sunset,
                label = "SUNSET",
                value = sunsetTime,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun WeatherDetailCard(
    icon: Int,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.15f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = label,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = label,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 9.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = value,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
fun SunCard(
    icon: Int,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(130.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(100.dp)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = label,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}



@Composable
fun createMockWeatherData(
    cityName: String = "Surabaya",
    weatherCondition: String = "Clear",
    temperature: Int = 31
): WeatherModel {
    return WeatherModel(
        cityName = cityName,
        temp = temperature,
        humidityLevel = 75,
        windVelocity = 6,
        tempFeelsLike = temperature - 2,
        rainAmount = if (weatherCondition == "Rain") 2.5 else 0.0,
        pressureLevel = 1013,
        cloudCoverage = 20,
        weatherIcon = "",
        weatherCondition = weatherCondition,
        sunriseTime = 1695945600L,
        sunsetTime = 1695990000L
    )
}

@Preview(showBackground = true, showSystemUi = true, name = "Medan - Rain")
@Composable
fun PreviewWeatherRain() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                WeatherContent(
                    weather = createMockWeatherData(
                        cityName = "Medan",
                        weatherCondition = "Rain",
                        temperature = 28
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Full App")
@Composable
fun previewWeather() {
    viewWeather()
}

