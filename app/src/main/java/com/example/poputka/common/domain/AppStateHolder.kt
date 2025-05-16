package com.example.poputka.common.domain

import com.example.poputka.common.domain.repository.AppPreferencesStateHolder
import com.example.poputka.feature_settings.domain.NotificationSettingsStateHolder
import com.example.poputka.feature_settings.domain.PersonalInfoStateHolder
import com.example.poputka.feature_daily_goal.domain.WeatherPreferencesStateHolder

interface AppStateHolder {
    val personalInfoStateHolder: PersonalInfoStateHolder
    val appPreferencesStateHolder: AppPreferencesStateHolder
    val notificationSettingsStateHolder: NotificationSettingsStateHolder
    val weatherPreferencesStateHolder: WeatherPreferencesStateHolder
}