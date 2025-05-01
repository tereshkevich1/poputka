package com.example.poputka.common.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.poputka.feature_notifications.data.local.NotificationsDao
import com.example.poputka.feature_notifications.data.models.NotificationEntity

@Database(entities = [NotificationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notificationsDao(): NotificationsDao

    companion object {
        const val DATABASE_NAME = "app_db"
    }
}