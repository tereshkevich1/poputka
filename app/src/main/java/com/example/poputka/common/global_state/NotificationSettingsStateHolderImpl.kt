package com.example.poputka.common.global_state

import com.example.poputka.common.domain.repository.AppDataStoreSource
import com.example.poputka.common.domain.repository.AppPreferencesStateHolder
import com.example.poputka.feature_settings.domain.NotificationSettingsStateH
import com.example.poputka.feature_settings.domain.NotificationSettingsStateHolder
import com.example.poputka.feature_settings.domain.PersonalInfoStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class NotificationSettingsStateHolderImpl @Inject constructor(
    private val appDataStoreSource: AppDataStoreSource
) : NotificationSettingsStateHolder {

    private val _notificationSettingsFlow = appDataStoreSource
        .booleanFlow(NOTIFICATIONS_ENABLED_KEY)
        .map { enabled ->
            NotificationSettingsStateH(notificationsEnabled = enabled)
        }
        .stateIn(
            scope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NotificationSettingsStateH()
        )

    override val notificationSettingsFlow: StateFlow<NotificationSettingsStateH>
        get() = _notificationSettingsFlow

    override suspend fun updateNotificationsEnabled(isEnabled: Boolean) {
        appDataStoreSource.putBoolean(NOTIFICATIONS_ENABLED_KEY, isEnabled)
    }

    companion object {
        private const val NOTIFICATIONS_ENABLED_KEY = "pref_key_notifications_enabled"
    }
}

interface AppStateHolder {
    val personalInfoStateHolder: PersonalInfoStateHolder
    val appPreferencesStateHolder: AppPreferencesStateHolder
    val notificationSettingsStateHolder: NotificationSettingsStateHolder
}
