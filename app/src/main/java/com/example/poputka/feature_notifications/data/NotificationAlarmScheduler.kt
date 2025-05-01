package com.example.poputka.feature_notifications.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.example.poputka.feature_notifications.domain.AlarmScheduler
import com.example.poputka.feature_notifications.domain.models.ReminderItem
import javax.inject.Inject

class NotificationAlarmScheduler @Inject constructor(private val context: Context) :
    AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun createPendingIntent(reminderItem: ReminderItem): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)

        return PendingIntent.getBroadcast(
            context,
            reminderItem.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun schedule(reminderItem: ReminderItem) {
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            reminderItem.time,
            AlarmManager.INTERVAL_DAY,
            createPendingIntent(reminderItem)
        )
        Log.d("shedule", "ешьу - ${reminderItem.time}   -  id - ${reminderItem.id}")
    }

    override fun cancel(reminderItem: ReminderItem) {
        alarmManager.cancel(createPendingIntent(reminderItem))
    }
}

