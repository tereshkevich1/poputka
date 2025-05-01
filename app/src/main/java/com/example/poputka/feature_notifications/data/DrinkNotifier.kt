package com.example.poputka.feature_notifications.data

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.poputka.R

class DrinkNotifier(
    notificationManager: NotificationManager,
    private val context: Context
) : Notifier(notificationManager) {

    override val notificationChannelId: String = "drink_channel_id"
    override val notificationChannelName: String = "Drink Notification"
    override val notificationId: Int = 200

    override fun buildNotification(): Notification {
        return NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle(getNotificationTitle())
            .setContentText(getNotificationMessage())
            .setSmallIcon(R.drawable.baseline_water_drop)
            .build()
    }

    override fun getNotificationTitle(): String {
        return context.getString(R.string.notification_title_hydration)
    }

    override fun getNotificationMessage(): String {
        return context.getString(R.string.notification_message_hydration)
    }
}