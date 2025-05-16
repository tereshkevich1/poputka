package com.example.poputka.feature_daily_goal.data.mappers

import com.example.poputka.feature_daily_goal.data.dto.WeatherResponse
import com.example.poputka.feature_daily_goal.domain.models.WeatherInfo

fun WeatherResponse.toDomain(): WeatherInfo {
    val avgTemp = hourly.temperature2m.average()
    val avgHumidity = hourly.relativeHumidity2m.average()
    return WeatherInfo(avgTemp, avgHumidity, elevation)
}