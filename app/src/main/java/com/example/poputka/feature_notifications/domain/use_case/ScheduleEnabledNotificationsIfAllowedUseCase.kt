package com.example.poputka.feature_notifications.domain.use_case

import com.example.poputka.common.domain.AppStateHolder
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ScheduleEnabledNotificationsIfAllowedUseCase @Inject constructor(
    private val appStateHolder: AppStateHolder,
    private val scheduleAllEnabledNotifications: ScheduleAllEnabledNotifications
) {
    suspend operator fun invoke() {
        val isNotificationEnabled = appStateHolder
            .notificationSettingsStateHolder
            .notificationSettingsFlow
            .first()
            .notificationsEnabled

        if (isNotificationEnabled) scheduleAllEnabledNotifications()
    }
}