package com.example.poputka.feature_notifications.domain.use_case

import com.example.poputka.feature_notifications.data.repository.NotificationsRepository
import com.example.poputka.feature_notifications.domain.AlarmScheduler
import com.example.poputka.feature_notifications.domain.models.ReminderItem
import javax.inject.Inject

class CancelAllEnabledNotifications @Inject constructor(
    private val alarmScheduler: AlarmScheduler,
    private val notificationsRepository: NotificationsRepository
) {
    suspend operator fun invoke() {
        val notifications = notificationsRepository.getAllNotifications().filter { it.isEnabled }
        notifications.forEach { notification ->
            val reminderItem = ReminderItem(
                id = notification.id,
                time = notification.time
            )
            alarmScheduler.cancel(reminderItem)
        }
    }
}