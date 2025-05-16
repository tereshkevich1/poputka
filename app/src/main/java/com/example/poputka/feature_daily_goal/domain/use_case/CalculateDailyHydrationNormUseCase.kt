package com.example.poputka.feature_daily_goal.domain.use_case

import com.example.poputka.common.domain.AppStateHolder
import com.example.poputka.feature_settings.domain.model.ActivityLevel
import com.example.poputka.feature_settings.domain.model.Gender
import kotlinx.coroutines.flow.first
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class CalculateDailyHydrationNormUseCase @Inject constructor(
    private val appStateHolder: AppStateHolder
) {
    suspend operator fun invoke(): Double? {
        val prefs = appStateHolder.appPreferencesStateHolder
        val appPrefsInfo = prefs.getAppPreferencesSnapshot()

        if (!appPrefsInfo.autoCalculation) return null

        val weather = appStateHolder.weatherPreferencesStateHolder.getWeatherPreferencesSnapshot()
        val info = appStateHolder.personalInfoStateHolder.personalInfoFlow.first()

        val weightKg = info.weight / 1000.0
        val heightCm = info.height
        val age = calculateAge(info.birthday)

        val pal = when (info.activityLevel) {
            ActivityLevel.VERY_LOW -> 1.2
            ActivityLevel.LOW -> 1.4
            ActivityLevel.MODERATE -> 1.6
            ActivityLevel.INTENSIVE -> 1.8
            ActivityLevel.VERY_INTENSIVE -> 2.0
        }

        val athleteStatus = if (info.activityLevel >= ActivityLevel.INTENSIVE) 1 else 0
        val hdiCode = HDI_MEDIUM

        val ffm = if (info.gender == Gender.MAN) {
            0.407 * weightKg + 0.267 * heightCm - 19.2
        } else {
            0.252 * weightKg + 0.473 * heightCm - 48.3
        }

        val temperature = weather.temperature ?: DEFAULT_TEMPERATURE
        val humidity = weather.humidity ?: DEFAULT_HUMIDITY
        val altitude = weather.altitude ?: DEFAULT_ALTITUDE

        return (861.9 * pal +
                37.34 * ffm +
                4.288 * humidity +
                699.7 * athleteStatus +
                105.0 * hdiCode +
                0.5140 * altitude -
                0.3625 * age * age +
                29.42 * age +
                1.937 * temperature * temperature -
                23.15 * temperature -
                984.8) * 0.75
    }

    private fun calculateAge(birthday: Long): Double {
        val birthDate = Instant.ofEpochMilli(birthday).atZone(ZoneId.systemDefault()).toLocalDate()
        return ChronoUnit.YEARS.between(birthDate, LocalDate.now()).toDouble()
    }

    private companion object {
        const val HDI_MEDIUM = 1
        const val DEFAULT_TEMPERATURE = 15.0
        const val DEFAULT_HUMIDITY = 50.0
        const val DEFAULT_ALTITUDE = 50.0
    }
}