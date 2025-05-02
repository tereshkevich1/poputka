package com.example.poputka.feature_notifications.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            context?.let {
                val workRequest =
                    OneTimeWorkRequestBuilder<NotificationWorkManager>().setInitialDelay(
                        Duration.ofSeconds(10)
                    ).setBackoffCriteria(
                        BackoffPolicy.LINEAR,
                        Duration.ofSeconds(30)
                    ).build()
                WorkManager.getInstance(it).enqueue(workRequest)
            }
        }
    }
}

