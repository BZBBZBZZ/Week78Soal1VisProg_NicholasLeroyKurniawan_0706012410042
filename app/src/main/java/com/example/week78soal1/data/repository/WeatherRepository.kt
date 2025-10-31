package com.example.week78soal1.data.repository

import com.example.week78soal1.container.WeatherContainer
import com.example.week78soal1.data.service.WeatherApiService
import com.example.week78soal1.ui.model.WeatherModel

class WeatherRepository(
    private val apiService: WeatherApiService
) {
    private val weatherApiKey = "0728b19dbcf4f98239a36ead537a65dd"

    suspend fun fetchWeather(cityName: String): WeatherModel? {
        val apiResponse = apiService.getWeather(cityName = cityName, apiKey = weatherApiKey)

        if (apiResponse.isSuccessful && apiResponse.body() != null) {
            val weatherData = apiResponse.body()!!
            return WeatherModel(
                cityName = weatherData.name,
                temp = weatherData.main.temp.toInt(),
                humidityLevel = weatherData.main.humidity,
                windVelocity = weatherData.wind.speed.toInt(),
                tempFeelsLike = weatherData.main.feels_like.toInt(),
                rainAmount = weatherData.rain?.`1h` ?: 0.0,
                pressureLevel = weatherData.main.pressure,
                cloudCoverage = weatherData.clouds.all,
                weatherIcon = "${WeatherContainer.ICON_BASE_URL}${weatherData.weather.firstOrNull()?.icon}@2x.png",
                weatherCondition = weatherData.weather.firstOrNull()?.main ?: "",
                sunriseTime = weatherData.sys.sunrise.toLong(),
                sunsetTime = weatherData.sys.sunset.toLong()
            )
        }
        return null
    }
}

