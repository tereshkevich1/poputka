package com.example.poputka.feature_daily_goal.domain

import android.location.Location

interface LocationClient {
    suspend fun getCurrentLocation(): Location?
}