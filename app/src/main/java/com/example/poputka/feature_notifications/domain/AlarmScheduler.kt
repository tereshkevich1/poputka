package com.example.poputka.feature_notifications.domain

import android.app.PendingIntent
import com.example.poputka.feature_notifications.domain.models.ReminderItem

interface AlarmScheduler {
    fun createPendingIntent(reminderItem: ReminderItem): PendingIntent

    fun schedule(reminderItem: ReminderItem)

    fun cancel(reminderItem: ReminderItem)
}