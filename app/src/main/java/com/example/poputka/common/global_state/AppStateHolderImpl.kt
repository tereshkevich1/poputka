package com.example.poputka.common.global_state

import com.example.poputka.common.domain.AppStateHolder
import com.example.poputka.common.domain.repository.AppDataStoreSource
import com.example.poputka.common.domain.repository.AppPreferencesStateHolder
import com.example.poputka.common.global_state.local_settings_state.PersonalInfoStateHolderImpl
import com.example.poputka.feature_settings.domain.NotificationSettingsStateHolder
import com.example.poputka.feature_settings.domain.PersonalInfoStateHolder
import com.example.poputka.feature_weather.domain.WeatherPreferencesStateHolder
import javax.inject.Inject

class AppStateHolderImpl @Inject constructor(
    appDataStoreSource: AppDataStoreSource
) : AppStateHolder {

    override val personalInfoStateHolder: PersonalInfoStateHolder =
        PersonalInfoStateHolderImpl(appDataStoreSource)

    override val appPreferencesStateHolder: AppPreferencesStateHolder =
        AppPreferencesStateHolderImpl(appDataStoreSource)

    override val notificationSettingsStateHolder: NotificationSettingsStateHolder =
        NotificationSettingsStateHolderImpl(appDataStoreSource)

    override val weatherPreferencesStateHolder: WeatherPreferencesStateHolder =
        WeatherPreferencesStateHolderImpl(appDataStoreSource)
}