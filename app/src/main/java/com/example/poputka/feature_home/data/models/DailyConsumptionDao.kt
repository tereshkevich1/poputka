package com.example.poputka.feature_home.data.models

import androidx.room.ColumnInfo

data class DailyConsumptionDao(
    @ColumnInfo(name = "totalVolume") val totalHydrationVolume: Int,
    @ColumnInfo(name = "date") val date: String
)