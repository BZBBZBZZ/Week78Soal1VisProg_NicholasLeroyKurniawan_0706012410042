package com.example.week78soal1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week78soal1.data.container.WeatherContainer
import com.example.week78soal1.data.repository.WeatherRepository
import com.example.week78soal1.ui.model.WeatherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class WeatherUiState(
    val weatherData: WeatherModel? = null,
    val hasError: Boolean = false,
    val isLoading: Boolean = false
)

class WeatherViewModel : ViewModel() {
    private val weatherRepository = WeatherRepository(WeatherContainer.weatherService)
    private val _state = MutableStateFlow(WeatherUiState())
    val state: StateFlow<WeatherUiState> = _state

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    fun updateSearchText(text: String) {
        _searchText.value = text
    }

    fun loadWeather(cityName: String) {
        if (cityName.isBlank()) return

        viewModelScope.launch {
            _state.value = WeatherUiState(isLoading = true)

            val weatherInfo = weatherRepository.fetchWeather(cityName.trim())
            if (weatherInfo != null) {
                _state.value = WeatherUiState(weatherData = weatherInfo)
            } else {
                _state.value = WeatherUiState(hasError = true)
            }
        }
    }
}
