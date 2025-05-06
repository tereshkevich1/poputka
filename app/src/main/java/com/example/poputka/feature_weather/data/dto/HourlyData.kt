package com.example.poputka.feature_weather.data.dto

import com.google.gson.annotations.SerializedName

data class HourlyData(
    val time: List<String>,
    @SerializedName("temperature_2m")
    val temperature2m: List<Double>,
    @SerializedName("relative_humidity_2m")
    val relativeHumidity2m: List<Int>
)