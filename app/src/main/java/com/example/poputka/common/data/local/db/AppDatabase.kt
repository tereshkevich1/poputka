package com.example.poputka.common.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.poputka.feature_home.data.local.ConsumptionDao
import com.example.poputka.feature_home.data.models.ConsumptionEntity
import com.example.poputka.feature_notifications.data.local.NotificationsDao
import com.example.poputka.feature_notifications.data.models.NotificationEntity

@Database(entities = [NotificationEntity::class, ConsumptionEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notificationsDao(): NotificationsDao
    abstract fun consumptionDao(): ConsumptionDao

    companion object {
        const val DATABASE_NAME = "app_db"
    }
}