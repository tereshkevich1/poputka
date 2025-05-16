package com.example.poputka.feature_daily_goal.data.dto

import kotlinx.serialization.SerialName


data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val elevation: Double,
    val timezone: String,
    @SerialName("utc_offset_seconds")
    val utcOffsetSeconds: Int,
    @SerialName("generationtime_ms")
    val generationTimeMs: Double,
    val hourly: HourlyData
)


