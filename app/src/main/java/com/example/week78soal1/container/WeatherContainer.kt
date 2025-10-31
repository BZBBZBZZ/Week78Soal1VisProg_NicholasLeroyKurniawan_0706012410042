package com.example.week78soal1.container

import com.example.week78soal1.data.service.WeatherApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherContainer {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val ICON_BASE_URL = "https://openweathermap.org/img/wn/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}