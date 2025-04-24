package com.example.poputka.feature_settings.presentation.settings_screen.bottom_sheets.daily_goal_bottom_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poputka.core.domain.Result
import com.example.poputka.core.domain.repository.AppPreferencesStateHolder
import com.example.poputka.core.presentation.models.asUiText
import com.example.poputka.feature_settings.domain.use_case.ValidateInputUseCase
import com.example.poputka.feature_settings.presentation.personal_settings_screen.models.errors.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyGoalViewModel @Inject constructor(
    private val appPreferencesStateHolder: AppPreferencesStateHolder,
    private val validateInputUseCase: ValidateInputUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(DailyGoalState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        DailyGoalState()
    )

    init {
        viewModelScope.launch {
            appPreferencesStateHolder.appPrefFlow.collect { domainState ->
                _state.update { currentUiState ->
                    currentUiState.copy(
                        autoCalculation = domainState.autoCalculation,
                        currentGoal = domainState.goalSetting.toString(),
                        currentVolumeUnit = domainState.volumeUnitSetting.asUiText()
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

            is DailyGoalAction.OnGoalValueChange -> updateDailyGoal(action.value)
            DailyGoalAction.OnDialogConfirm -> {
                saveDailyGoalInBottomSheet()
                closeDialog()
            }

            DailyGoalAction.OnDialogDismiss -> {
                toggleAutoCalculation(!_state.value.autoCalculation)
                closeDialog()
            }
            DailyGoalAction.OnCancelClick -> resetToCurrentPreferences()
            DailyGoalAction.OnSaveClick -> saveGoal()
        }
    }

    private fun saveGoal() {
        if (!_state.value.autoCalculation) {
            val newGoal = _state.value.currentGoal.toDoubleOrNull()
            newGoal?.let {
                viewModelScope.launch {
                    appPreferencesStateHolder.saveGoal(newGoal)
                    appPreferencesStateHolder.saveAutoCalculation(_state.value.autoCalculation)
                }
            }
        } else {
            viewModelScope.launch {
                appPreferencesStateHolder.saveAutoCalculation(_state.value.autoCalculation)
            }
        }
    }

    private fun toggleAutoCalculation(checked: Boolean) {
        _state.update {
            it.copy(autoCalculation = checked)
        }
    }

    private fun showDialog(checked: Boolean) {
        if (!checked) {
            _state.update {
                it.copy(showDialog = true)
            }
        }
    }

    private fun saveDailyGoalInBottomSheet() {
        _state.update {
            it.copy(currentGoal = it.inputValue)
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

    private fun updateDailyGoal(input: String) {
        val result = validateInputUseCase(input, 10000)
        _state.update { currentState ->
            when (result) {
                is Result.Success -> {
                    val quantity = result.data
                    currentState.copy(
                        inputValue = quantity?.toString() ?: "",
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
            appPreferencesStateHolder.appPrefFlow.collect { domainState ->
                _state.update {
                    it.copy(
                        autoCalculation = domainState.autoCalculation,
                        currentGoal = domainState.goalSetting.toString(),
                        currentVolumeUnit = domainState.volumeUnitSetting.asUiText(),
                        inputValue = "",
                        errorMessage = null,
                        showDialog = false
                    )
                }
                this.cancel()
            }
        }
    }
}