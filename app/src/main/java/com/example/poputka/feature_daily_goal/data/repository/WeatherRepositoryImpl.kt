package com.example.poputka.feature_daily_goal.data.repository

import android.util.Log
import com.example.poputka.common.domain.AppStateHolder
import com.example.poputka.core.data.networking.DataError
import com.example.poputka.core.domain.Result
import com.example.poputka.feature_daily_goal.data.data_source.WeatherDataSource
import com.example.poputka.feature_daily_goal.data.mappers.toDomain
import com.example.poputka.feature_daily_goal.domain.models.WeatherInfo
import com.example.poputka.feature_daily_goal.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val dataSource: WeatherDataSource,
    private val appStateHolder: AppStateHolder
) :
    WeatherRepository {
    override suspend fun fetchAndStoreCurrentWeather(
        latitude: Double,
        longitude: Double
    ): Result<WeatherInfo, DataError.Network> =
        withContext(Dispatchers.IO) {
            dataSource.getDailyForecast(latitude, longitude).let {
                when (it) {
                    is Result.Success -> {
                        val weatherInfo = it.data.toDomain()
                        Log.d("WeatherRepository", "weatherInfo: $it")
                        Log.d("WeatherRepository", "weatherInfo: $weatherInfo")
                        appStateHolder.weatherPreferencesStateHolder.setWeather(
                            weatherInfo.averageTemperature,
                            weatherInfo.averageHumidity,
                            weatherInfo.altitude
                        )
                        appStateHolder.weatherPreferencesStateHolder.setCoordinates(
                            it.data.latitude,
                            it.data.longitude
                        )
                        Result.Success(weatherInfo)
                    }

                    is Result.Error -> {
                        Result.Error(it.error)
                    }
                }
            }
        }
}