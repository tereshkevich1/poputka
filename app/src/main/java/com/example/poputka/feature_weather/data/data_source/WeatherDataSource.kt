package com.example.poputka.feature_weather.data.data_source

import com.example.poputka.core.data.networking.DataError
import com.example.poputka.core.domain.Result
import com.example.poputka.feature_weather.data.dto.WeatherResponse

interface WeatherDataSource {
    suspend fun getDailyForecast(
        latitude: Double,
        longitude: Double
    ): Result<WeatherResponse, DataError.Network>
}

