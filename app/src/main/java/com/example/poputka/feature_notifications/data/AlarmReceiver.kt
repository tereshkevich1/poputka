package com.example.poputka.feature_notifications.data

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver: BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmReceiver", "Alarm received!")
        context?.let {
            val notificationManager =
                it.getSystemService(NotificationManager::class.java)
            val drinkNotifier = DrinkNotifier(notificationManager, it)
            drinkNotifier.showNotification()
        }
    }
}