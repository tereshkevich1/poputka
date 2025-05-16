package com.example.poputka.feature_home.presentation.home_screen

import androidx.lifecycle.viewModelScope
import com.example.poputka.common.domain.AppStateHolder
import com.example.poputka.common.presentation.models.mappers.toDisplayableVolume
import com.example.poputka.core.presentation.BaseViewModel
import com.example.poputka.feature_home.domain.use_case.GetTodayConsumptionFlowUseCase
import com.example.poputka.feature_home.presentation.home_screen.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTodayConsumptionFlowUseCase: GetTodayConsumptionFlowUseCase,
    appStateHolder: AppStateHolder
) : BaseViewModel<HomeEvent>() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        HomeUiState()
    )

    private val appPrefs = appStateHolder.appPreferencesStateHolder.appPrefFlow

    init {
        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {
            combine(
                getTodayConsumptionFlowUseCase(),
                appPrefs
            ) { summary, prefs ->
                val unit = prefs.volumeUnitSetting

                val goal = unit.convertFromMilliliters(prefs.goalSetting).toFloat()
                val currentProgress = unit.convertFromMilliliters(summary.totalMl).toFloat()

                HomeUiState(
                    goal = goal,
                    currentProgress = currentProgress,
                    unit = unit,
                    totalHydro = summary.totalHydroMl.toDisplayableVolume(unit),
                    totalToday = summary.totalMl.toDisplayableVolume(unit),
                    log = summary.consumptions.map { it.toUi(unit) }
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnRefresh -> {}
            is HomeAction.OnDrinkClick -> {
                // future navigation or info
            }
        }
    }
}