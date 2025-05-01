package com.example.poputka.feature_settings.presentation.notification_settings_screen

import androidx.lifecycle.viewModelScope
import com.example.poputka.common.global_state.AppStateHolder
import com.example.poputka.core.presentation.BaseViewModel
import com.example.poputka.feature_notifications.data.repository.NotificationsRepository
import com.example.poputka.feature_notifications.domain.AlarmScheduler
import com.example.poputka.feature_notifications.domain.models.ReminderItem
import com.example.poputka.feature_notifications.domain.use_case.GetReminderTimeInMillisUseCase
import com.example.poputka.feature_settings.presentation.notification_settings_screen.models.NotificationUi
import com.example.poputka.feature_settings.presentation.notification_settings_screen.models.mappers.toDisplayableTime
import com.example.poputka.feature_settings.presentation.notification_settings_screen.models.mappers.toDomain
import com.example.poputka.feature_settings.presentation.notification_settings_screen.models.mappers.toNotificationUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    appStateHolder: AppStateHolder,
    private val notificationAlarmScheduler: AlarmScheduler,
    private val notificationsRepository: NotificationsRepository,
    private val getReminderTimeInMillisUseCase: GetReminderTimeInMillisUseCase
) :
    BaseViewModel<NotificationScreenEvent>() {
    private val _state = MutableStateFlow(NotificationSettingsState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            NotificationSettingsState()
        )

    private val notificationStateHolder = appStateHolder.notificationSettingsStateHolder

    init {
        viewModelScope.launch {
            notificationStateHolder.notificationSettingsFlow.collect { domainState ->
                _state.update { currentUiState ->
                    currentUiState.copy(
                        isNotificationEnabled = domainState.notificationsEnabled
                    )
                }
            }
        }
        loadNotifications()
    }

    fun onAction(action: NotificationScreenAction) {
        when (action) {
            is NotificationScreenAction.OnNotificationClick -> onTimeNotificationClick(action.notification)

            is NotificationScreenAction.OnNotificationToggle -> toggleNotification(
                action.notificationId,
                action.isEnabled
            )

            NotificationScreenAction.OnTimePickerDismissed -> closeTimePicker()
            is NotificationScreenAction.OnTimeSelected -> updateNotificationTime(
                action.hour,
                action.minute
            )

            is NotificationScreenAction.OnNotificationSettingsToggle -> changeNotificationSettings(
                action.isEnabled
            )
        }
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            val notification = notificationsRepository.getAllNotifications()
                .map { notification ->
                    notification.toNotificationUi()
                }
            _state.update {
                it.copy(
                    notifications = notification
                )
            }
        }
    }

    private fun closeTimePicker() {
        _state.update {
            it.copy(showTimePicker = false)
        }
    }

    private fun updateNotificationTime(hour: Int, minute: Int) {
        _state.value.selectedNotification?.let { selectedNotification ->
            val timeInMillis = getReminderTimeInMillisUseCase(hour, minute)
            val updatedNotification =
                selectedNotification.copy(time = timeInMillis.toDisplayableTime())

            _state.update { state ->
                state.copy(
                    selectedNotification = null,
                    showTimePicker = false,
                    notifications = state.notifications.map {
                        if (it.id == updatedNotification.id) updatedNotification else it
                    }
                )
            }

            updateNotificationAlarm(updatedNotification)

            viewModelScope.launch {
                notificationsRepository.upsertNotification(updatedNotification.toDomain())
            }
        }
    }

    private fun onTimeNotificationClick(notification: NotificationUi) {
        _state.update { state ->
            state.copy(
                selectedNotification = notification,
                showTimePicker = true,
            )
        }
    }

    private fun toggleNotification(notification: NotificationUi, isEnabled: Boolean) {
        val updatedNotification =
            notification.copy(isEnabled = isEnabled)

        _state.update { state ->
            state.copy(notifications = state.notifications.map {
                if (it.id == notification.id) updatedNotification else it
            }
            )
        }

        updateNotificationAlarm(updatedNotification)

        viewModelScope.launch {
            notificationsRepository.upsertNotification(updatedNotification.toDomain())
        }
    }

    private fun updateNotificationAlarm(notification: NotificationUi) {
        val reminderItem = ReminderItem(
            id = notification.id,
            time = notification.time.value
        )
        if (notification.isEnabled) {
            notificationAlarmScheduler.schedule(reminderItem)
        } else {
            notificationAlarmScheduler.cancel(reminderItem)
        }
    }

    private fun changeNotificationSettings(isEnabled: Boolean) {
        _state.update {
            it.copy(isNotificationEnabled = isEnabled)
        }
        viewModelScope.launch {
            notificationStateHolder.updateNotificationsEnabled(isEnabled)
        }
    }
}
