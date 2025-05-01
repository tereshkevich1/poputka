package com.example.poputka.feature_notifications.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.poputka.feature_notifications.data.models.NotificationEntity

@Dao
interface NotificationsDao {
    @Query("SELECT * From notifications")
    suspend fun getNotifications(): List<NotificationEntity>

    @Upsert
    fun upsertNotifications(notifications: List<NotificationEntity>)

    @Upsert
    suspend fun upsertNotification(notification: NotificationEntity)
}


