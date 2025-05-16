package com.example.poputka.common.global_state

import com.example.poputka.common.domain.repository.AppDataStoreSource
import com.example.poputka.feature_daily_goal.domain.WeatherPreferencesStateHolder
import com.example.poputka.feature_daily_goal.domain.models.WeatherPreferencesState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class WeatherPreferencesStateHolderImpl @Inject constructor(
    private val appDataStoreSource: AppDataStoreSource
) : WeatherPreferencesStateHolder {

    private val _weatherPrefFlow: StateFlow<WeatherPreferencesState> = combine(
        appDataStoreSource.booleanFlow(WEATHER_ENABLED),
        appDataStoreSource.doubleFlow(LATITUDE),
        appDataStoreSource.doubleFlow(LONGITUDE),
        appDataStoreSource.doubleFlow(TEMPERATURE),
        appDataStoreSource.doubleFlow(HUMIDITY),
        appDataStoreSource.doubleFlow(ALTITUDE),
        appDataStoreSource.doubleFlow(HDI)
    ) { flows ->
        WeatherPreferencesState(
            isWeatherAdjustmentEnabled = flows[0] as Boolean,
            latitude = flows[1] as Double,
            longitude = flows[2] as Double,
            temperature = flows[3] as Double,
            humidity = flows[4] as Double,
            altitude = flows[5] as Double
        )
    }.stateIn(
        scope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WeatherPreferencesState()
    )

    override val weatherPrefFlow: StateFlow<WeatherPreferencesState> get() = _weatherPrefFlow

    override suspend fun setWeatherAdjustmentEnabled(enabled: Boolean) {
        appDataStoreSource.putBoolean(WEATHER_ENABLED, enabled)
    }

    override suspend fun setCoordinates(latitude: Double, longitude: Double) {
        appDataStoreSource.putDouble(LATITUDE, latitude)
        appDataStoreSource.putDouble(LONGITUDE, longitude)
    }

    override suspend fun setWeather(
        temperature: Double,
        humidity: Double,
        altitude: Double
    ) {
        appDataStoreSource.putDouble(TEMPERATURE, temperature)
        appDataStoreSource.putDouble(HUMIDITY, humidity)
        appDataStoreSource.putDouble(ALTITUDE, altitude)
    }

    override suspend fun getWeatherPreferencesSnapshot(): WeatherPreferencesState {
        return WeatherPreferencesState(
            isWeatherAdjustmentEnabled = appDataStoreSource.getBooleanValue(WEATHER_ENABLED, false),
            latitude = appDataStoreSource.getDoubleValue(LATITUDE, DEFAULT_DOUBLE_VALUE),
            longitude = appDataStoreSource.getDoubleValue(LONGITUDE, DEFAULT_DOUBLE_VALUE),
            temperature = appDataStoreSource.getDoubleValue(TEMPERATURE, DEFAULT_DOUBLE_VALUE),
            humidity = appDataStoreSource.getDoubleValue(HUMIDITY, DEFAULT_DOUBLE_VALUE),
            altitude = appDataStoreSource.getDoubleValue(ALTITUDE, DEFAULT_DOUBLE_VALUE)
        )
    }

    companion object {
        const val DEFAULT_DOUBLE_VALUE = 0.0
        const val WEATHER_ENABLED = "weather_enabled"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val TEMPERATURE = "temperature"
        const val HUMIDITY = "humidity"
        const val ALTITUDE = "altitude"
        const val HDI = "hdi"
    }
}
