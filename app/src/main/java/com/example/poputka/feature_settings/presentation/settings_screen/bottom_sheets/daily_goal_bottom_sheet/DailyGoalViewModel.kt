package com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.daily_goal_bottom_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poputka.common.domain.AppStateHolder
import com.example.poputka.common.presentation.models.mappers.toDisplayableVolume
import com.example.poputka.core.domain.Result
import com.example.poputka.feature_daily_goal.domain.use_case.SaveHydrationGoalUseCase
import com.example.poputka.feature_settings.domain.use_case.ValidateVolumeInputUseCase
import com.example.poputka.feature_settings.presentation.personal_settings_screen.models.errors.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyGoalViewModel @Inject constructor(
    appStateHolder: AppStateHolder,
    private val validateVolumeInputUseCase: ValidateVolumeInputUseCase,
    private val saveHydrationGoalUseCase: SaveHydrationGoalUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(DailyGoalState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            DailyGoalState()
        )
    private val appPref = appStateHolder.appPreferencesStateHolder

    init {
        viewModelScope.launch {
            appPref.appPrefFlow.collect { domainState ->
                val goalMl =
                    domainState.goalSetting.toDisplayableVolume(domainState.volumeUnitSetting)
                _state.update { currentUiState ->
                    currentUiState.copy(
                        autoCalculation = domainState.autoCalculation,
                        currentGoal = goalMl,
                        currentVolumeUnit = domainState.volumeUnitSetting
                    )
                }
            }
        }
    }

    fun onAction(action: DailyGoalAction) {
        when (action) {
            is DailyGoalAction.OnAutoToggle -> {
                toggleAutoCalculation(action.enabled)
                showDialog(action.enabled)
            }

            is DailyGoalAction.OnGoalValueChange -> updateInputDailyGoal(action.value)
            DailyGoalAction.OnDialogConfirm -> {
                saveDailyGoalInBottomSheet()
                closeDialog()
            }

            DailyGoalAction.OnDialogDismiss -> {
                toggleAutoCalculation(!_state.value.autoCalculation)
                closeDialog()
            }

            DailyGoalAction.OnCancelClick -> resetToCurrentPreferences()
            DailyGoalAction.OnSaveClick -> saveManualGoal()
        }
    }

    private fun saveManualGoal() {
        if (_state.value.autoCalculation) return

        viewModelScope.launch {
            appPref.saveGoal(_state.value.currentGoal.value)
            appPref.saveAutoCalculation(false)
        }
    }

    private fun toggleAutoCalculation(checked: Boolean) {
        if (checked) {
            viewModelScope.launch {
                appPref.saveAutoCalculation(true)
                saveHydrationGoalUseCase()
            }
        }
        _state.update {
            it.copy(autoCalculation = checked)
        }
    }

    private fun showDialog(checked: Boolean) {
        if (checked) return
        _state.update {
            it.copy(showDialog = true)
        }
    }

    private fun closeDialog() {
        _state.update {
            it.copy(
                inputValue = "",
                errorMessage = null,
                showDialog = false,
            )
        }
    }

    private fun saveDailyGoalInBottomSheet() {
        val newGoal = _state.value.inputValue.toDoubleOrNull()
        newGoal?.let {
            val newGoalMl =
                appPref.appPrefFlow.value.volumeUnitSetting.convertToMilliliters(newGoal)
                    .toDisplayableVolume(_state.value.currentVolumeUnit)
            _state.update {
                it.copy(currentGoal = newGoalMl)
            }
        }
    }

    private fun updateInputDailyGoal(input: String) {
        val result = validateVolumeInputUseCase(input, _state.value.currentVolumeUnit)
        _state.update { currentState ->
            when (result) {
                is Result.Success -> {
                    val value = result.data
                    currentState.copy(
                        inputValue = value ?: "",
                        errorMessage = null
                    )
                }

                is Result.Error -> currentState.copy(
                    inputValue = input,
                    errorMessage = result.error.asUiText(),
                )
            }
        }
    }

    private fun resetToCurrentPreferences() {
        viewModelScope.launch {
            val appPref = appPref.getAppPreferencesSnapshot()
            _state.update {
                it.copy(
                    autoCalculation = appPref.autoCalculation,
                    currentGoal = appPref.goalSetting.toDisplayableVolume(appPref.volumeUnitSetting),
                    currentVolumeUnit = appPref.volumeUnitSetting,
                    inputValue = "",
                    errorMessage = null,
                    showDialog = false
                )
            }
        }
    }
}