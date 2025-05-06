package com.example.poputka.feature_weather.domain

import android.location.Location

interface LocationClient {
    suspend fun getCurrentLocation(): Location?
}