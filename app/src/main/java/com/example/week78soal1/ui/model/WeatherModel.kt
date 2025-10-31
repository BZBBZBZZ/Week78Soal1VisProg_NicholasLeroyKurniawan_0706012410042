package com.example.week78soal1.ui.model

data class WeatherModel(
    val cityName: String = "",
    val temp: Int = 0,
    val humidityLevel: Int = 0,
    val windVelocity: Int = 0,
    val tempFeelsLike: Int = 0,
    val rainAmount: Double = 0.0,
    val pressureLevel: Int = 0,
    val cloudCoverage: Int = 0,
    val weatherIcon: String = "",
    val weatherCondition: String = "",
    val sunriseTime: Long = 0,
    val sunsetTime: Long = 0
)

