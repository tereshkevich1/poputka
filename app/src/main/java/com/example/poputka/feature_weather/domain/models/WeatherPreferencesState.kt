package com.example.poputka.feature_weather.domain.models

data class WeatherPreferencesState(
    val isWeatherAdjustmentEnabled: Boolean = false,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val temperature: Double? = null,
    val humidity: Double? = null,
    val altitude: Double? = null,
)