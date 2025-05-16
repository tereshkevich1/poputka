package com.example.poputka.feature_daily_goal.domain

import com.example.poputka.feature_daily_goal.domain.models.WeatherPreferencesState
import kotlinx.coroutines.flow.StateFlow

interface WeatherPreferencesStateHolder {
    val weatherPrefFlow: StateFlow<WeatherPreferencesState>
    suspend fun setWeatherAdjustmentEnabled(enabled: Boolean)
    suspend fun setCoordinates(latitude: Double, longitude: Double)
    suspend fun setWeather(temperature: Double, humidity: Double, altitude: Double)
    suspend fun getWeatherPreferencesSnapshot(): WeatherPreferencesState
}