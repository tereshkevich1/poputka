package com.example.poputka.feature_weather.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.example.poputka.feature_weather.data.util.hasLocationPermissions
import com.example.poputka.feature_weather.domain.LocationClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.tasks.await

class DefaultLocationClient(
    private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationClient {
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? {
        if (context.hasLocationPermissions()) {
            throw SecurityException("Missing location permissions")
        }

        val lastLocation = null
        return lastLocation ?: fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener) = this
                override fun isCancellationRequested() = false
            }
        ).await()
    }
}