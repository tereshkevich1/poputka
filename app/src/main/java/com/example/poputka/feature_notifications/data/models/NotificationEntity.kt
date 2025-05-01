package com.example.poputka.feature_notifications.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titleResId: Int,
    val time: Long,
    @ColumnInfo(name = "is_enabled")
    val isEnabled: Boolean
)