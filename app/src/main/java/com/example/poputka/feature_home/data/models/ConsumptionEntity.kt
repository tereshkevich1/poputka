package com.example.poputka.feature_home.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "consumption")
data class ConsumptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "drink_type")
    val drinkType: String,
    val volume: Int,
    val timestamp: Long
)




