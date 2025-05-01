package com.example.poputka.feature_notifications.data.repository

import com.example.poputka.feature_notifications.data.local.NotificationsDao
import com.example.poputka.feature_notifications.data.mappers.toDomain
import com.example.poputka.feature_notifications.data.mappers.toEntity
import com.example.poputka.feature_notifications.domain.models.Notification

class NotificationsRepositoryImpl(private val dao: NotificationsDao) : NotificationsRepository {
    override suspend fun getAllNotifications(): List<Notification> =
        dao.getNotifications().map { entity ->
            entity.toDomain()
        }

    override suspend fun upsertNotification(notification: Notification) {
        dao.upsertNotification(notification.toEntity())
    }
}

