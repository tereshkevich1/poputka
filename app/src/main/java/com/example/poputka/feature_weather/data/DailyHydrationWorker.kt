package com.example.poputka.feature_weather.data

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.poputka.feature_weather.domain.LocationClient
import com.example.poputka.feature_weather.domain.repository.WeatherRepository
import com.example.poputka.feature_weather.domain.use_case.SaveHydrationGoalUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DailyHydrationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val weatherRepository: WeatherRepository,
    private val defaultLocationClient: LocationClient,
    private val saveHydrationGoalUseCase: SaveHydrationGoalUseCase
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val location = defaultLocationClient.getCurrentLocation()
        location ?: return Result.failure()
        Log.d(
            TAG,
            "location - ${location.latitude} ${location.longitude}"
        )
        val result =
            weatherRepository.fetchAndStoreCurrentWeather(location.latitude, location.longitude)

        when (result) {
            is com.example.poputka.core.domain.Result.Error -> {
                Log.d(TAG, result.error.name)
                Result.failure()
            }

            is com.example.poputka.core.domain.Result.Success -> {
                Log.d(
                    TAG,
                    "humidity - ${result.data.averageHumidity} temperature - ${result.data.averageTemperature}  ${result.data.altitude}"
                )
                saveHydrationGoalUseCase()
            }
        }

        return Result.success()
    }

    companion object {
        const val TAG = "DailyHydrationWorker"
    }
}