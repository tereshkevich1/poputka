package com.example.poputka.feature_notifications.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.poputka.feature_notifications.domain.use_case.ScheduleEnabledNotificationsIfAllowedUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class NotificationWorkManager @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val scheduleEnabledNotificationsIfAllowedUseCase: ScheduleEnabledNotificationsIfAllowedUseCase
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            scheduleEnabledNotificationsIfAllowedUseCase()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}