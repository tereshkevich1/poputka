package com.example.poputka.feature_home.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drinks")
data class DrinkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val type: String,
    @ColumnInfo(name = "hydration_factor")
    val hydrationFactor: Float,
    val calories: Int
)

