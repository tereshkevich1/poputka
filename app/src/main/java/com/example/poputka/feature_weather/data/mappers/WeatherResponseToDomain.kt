package com.example.poputka.feature_weather.data.mappers

import com.example.poputka.feature_weather.data.dto.WeatherResponse
import com.example.poputka.feature_weather.domain.models.WeatherInfo

fun WeatherResponse.toDomain(): WeatherInfo {
    val avgTemp = hourly.temperature2m.average()
    val avgHumidity = hourly.relativeHumidity2m.average()
    return WeatherInfo(avgTemp, avgHumidity, elevation)
}