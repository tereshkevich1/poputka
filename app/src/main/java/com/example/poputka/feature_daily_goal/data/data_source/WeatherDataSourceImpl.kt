package com.example.poputka.feature_daily_goal.data.data_source

import com.example.poputka.core.data.networking.DataError
import com.example.poputka.core.data.networking.responseToResult
import com.example.poputka.core.domain.Result
import com.example.poputka.feature_daily_goal.data.dto.WeatherResponse
import javax.inject.Inject

class WeatherDataSourceImpl @Inject constructor(private val api: WeatherApi) : WeatherDataSource {
    override suspend fun getDailyForecast(
        latitude: Double,
        longitude: Double
    ): Result<WeatherResponse, DataError.Network> {
        return responseToResult {
            api.getDailyForecast(latitude, longitude)
        }
    }
}