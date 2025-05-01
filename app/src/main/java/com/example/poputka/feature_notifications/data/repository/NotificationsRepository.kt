package com.example.poputka.feature_notifications.data.repository


import com.example.poputka.feature_notifications.domain.models.Notification

interface NotificationsRepository {
    suspend fun getAllNotifications(): List<Notification>
    suspend fun upsertNotification(notification: Notification)
}