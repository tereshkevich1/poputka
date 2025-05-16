package com.example.poputka.feature_daily_goal.domain.repository

import com.example.poputka.core.data.networking.DataError
import com.example.poputka.core.domain.Result
import com.example.poputka.feature_daily_goal.domain.models.WeatherInfo

interface WeatherRepository {
    suspend fun fetchAndStoreCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Result<WeatherInfo, DataError.Network>
}