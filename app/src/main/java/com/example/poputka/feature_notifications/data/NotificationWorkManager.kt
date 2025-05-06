package com.example.poputka.feature_notifications.data

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.poputka.feature_notifications.domain.use_case.ScheduleEnabledNotificationsIfAllowedUseCase
import com.example.poputka.feature_weather.data.DefaultLocationClient
import com.google.android.gms.location.LocationServices
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorkManager @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val scheduleEnabledNotificationsIfAllowedUseCase: ScheduleEnabledNotificationsIfAllowedUseCase
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            Log.d(TAG, "work")

            val defaultLocationClient = DefaultLocationClient(
                context = applicationContext,
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                    applicationContext
                )
            )
            Log.d(TAG, defaultLocationClient.getCurrentLocation().toString())
            scheduleEnabledNotificationsIfAllowedUseCase()
            Result.success()
        } catch (e: Exception) {
            Log.d(TAG, "work_fail")
            Result.failure()
        }
    }

    companion object {
        const val TAG = "N_WORKER"
    }
}