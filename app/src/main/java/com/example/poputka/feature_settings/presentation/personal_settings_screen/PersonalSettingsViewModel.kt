package com.example.poputka.feature_settings.presentation.personal_settings_screen

import androidx.lifecycle.viewModelScope
import com.example.poputka.common.domain.AppStateHolder
import com.example.poputka.core.presentation.BaseViewModel
import com.example.poputka.feature_settings.domain.model.ActivityLevel
import com.example.poputka.feature_settings.domain.model.Gender
import com.example.poputka.feature_settings.presentation.personal_settings_screen.models.PersonalSettingsBottomSheet
import com.example.poputka.feature_weather.domain.use_case.SaveHydrationGoalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalSettingsViewModel @Inject constructor(
    appStateHolder: AppStateHolder,
    private val saveHydrationGoalUseCase: SaveHydrationGoalUseCase
) : BaseViewModel<PersonalSettingsEvent>() {
    private val _state = MutableStateFlow(PersonalSettingsScreenState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PersonalSettingsScreenState()
        )

    private val personalInfoStateHolder = appStateHolder.personalInfoStateHolder

    init {
        viewModelScope.launch {
            personalInfoStateHolder.personalInfoFlow.collect { domainState ->
                _state.update { currentUiState ->
                    currentUiState.copy(
                        display = domainState.toUi()
                    )
                }
            }
        }
    }

    fun onAction(action: PersonalSettingsAction) {
        when (action) {
            PersonalSettingsAction.OnActivityLevelClick -> showBottomSheet(
                PersonalSettingsBottomSheet.ACTIVITY_LEVEL
            )

            PersonalSettingsAction.OnBackClick -> sendEvent(PersonalSettingsEvent.NavigateToSettingsScreen)
            PersonalSettingsAction.OnBirthdayClick -> showBottomSheet(PersonalSettingsBottomSheet.BIRTHDAY)
            PersonalSettingsAction.OnGenderClick -> showBottomSheet(PersonalSettingsBottomSheet.GENDER)
            PersonalSettingsAction.OnHeightClick -> showBottomSheet(PersonalSettingsBottomSheet.HEIGHT)
            PersonalSettingsAction.OnWeightClick -> showBottomSheet(PersonalSettingsBottomSheet.WEIGHT)
            PersonalSettingsAction.OnBottomSheetClosed -> closeBottomSheet()

            is PersonalSettingsAction.OnActivityLevelSave -> saveActivityLevel(action.level)
            is PersonalSettingsAction.OnBirthdaySave -> saveBirthday(action.birthday)
            is PersonalSettingsAction.OnGenderSave -> saveGender(action.gender)
            is PersonalSettingsAction.OnHeightSave -> saveHeight(action.height)
            is PersonalSettingsAction.OnWeightSave -> saveWeight(
                action.integerPart,
                action.decimalPart
            )
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

    private fun saveActivityLevel(activityLevel: ActivityLevel) {
        viewModelScope.launch {
            personalInfoStateHolder.updateActivityLevel(activityLevel)
            saveHydrationGoalUseCase()
            closeBottomSheet()
        }
    }

    private fun saveBirthday(date: Long?) {
        date?.let {
            viewModelScope.launch {
                personalInfoStateHolder.updateBirthday(it)
                saveHydrationGoalUseCase()
            }
        } ?: sendEvent(PersonalSettingsEvent.ShowToast(""))
    }

    private fun saveGender(gender: Gender) {
        viewModelScope.launch {
            personalInfoStateHolder.updateGender(gender)
            saveHydrationGoalUseCase()
        }
        closeBottomSheet()
    }

    private fun saveHeight(heightText: String?) {
        val height = heightText?.toIntOrNull()
        height?.let {
            viewModelScope.launch {
                personalInfoStateHolder.updateHeight(it)
                saveHydrationGoalUseCase()
            }
            closeBottomSheet()
        } ?: sendEvent(PersonalSettingsEvent.ShowToast(""))
    }

    private fun saveWeight(integerPart: String, decimalPart: String) {
        val weightFloat = (integerPart + decimalPart.removePrefix("0")).toFloatOrNull()

        weightFloat?.let {
            viewModelScope.launch {
                personalInfoStateHolder.updateWeight(weightFloat)
                saveHydrationGoalUseCase()
            }
            closeBottomSheet()
        } ?: sendEvent(PersonalSettingsEvent.ShowToast(""))
    }
}

