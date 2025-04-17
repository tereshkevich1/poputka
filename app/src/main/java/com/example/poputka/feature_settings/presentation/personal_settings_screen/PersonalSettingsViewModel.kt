package com.example.poputka.feature_settings.presentation.personal_settings_screen

import androidx.lifecycle.viewModelScope
import com.example.poputka.core.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PersonalSettingsViewModel @Inject constructor() : BaseViewModel<PersonalSettingsEvent>() {
    private val _state = MutableStateFlow(PersonalSettingsState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        PersonalSettingsState()
    )

    fun onAction(action: PersonalSettingsAction) {
        when (action) {
            PersonalSettingsAction.OnActivityLevelClick -> TODO()
            PersonalSettingsAction.OnBackClick -> TODO()
            PersonalSettingsAction.OnBirthdayClick -> TODO()
            PersonalSettingsAction.OnGenderClick -> TODO()
            PersonalSettingsAction.OnHeightClick -> showBottomSheet(PersonalSettingsBottomSheet.HEIGHT)
            PersonalSettingsAction.OnWeightClick -> showBottomSheet(PersonalSettingsBottomSheet.WEIGHT)
            PersonalSettingsAction.OnBottomSheetClosed -> closeBottomSheet()
        }
    }

    private fun closeBottomSheet() {
        _state.update {
            it.copy(showBottomSheet = false)
        }
    }

    private fun showBottomSheet(bottomSheet: PersonalSettingsBottomSheet) {
        _state.update {
            it.copy(
                showBottomSheet = true,
                bottomSheet = bottomSheet
            )
        }
    }
}

enum class PersonalSettingsBottomSheet {
    BIRTHDAY,
    GENDER,
    HEIGHT,
    WEIGHT,
    ACTIVITY_LEVEL
}

