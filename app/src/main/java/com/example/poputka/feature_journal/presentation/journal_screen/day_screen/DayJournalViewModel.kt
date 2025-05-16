package com.example.poputka.feature_journal.presentation.journal_screen.day_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poputka.common.domain.AppStateHolder
import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.common.presentation.models.mappers.toDisplayableDateFull
import com.example.poputka.common.presentation.models.mappers.toDisplayableTime
import com.example.poputka.common.presentation.models.mappers.toDisplayableVolume
import com.example.poputka.feature_journal.presentation.charts.bar_chart.Bar
import com.example.poputka.feature_journal.presentation.charts.common.ChartModeConstants.DAY_BAR_COUNT
import com.example.poputka.feature_journal.presentation.journal_screen.DayScreenAction
import com.example.poputka.feature_journal.presentation.journal_screen.DrinkRecordUi
import com.example.poputka.feature_journal.presentation.journal_screen.GetConsumptionForDayFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZoneId
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DayJournalViewModel @Inject constructor(
    private val getConsumptionForDayFlowUseCase: GetConsumptionForDayFlowUseCase,
    appStateHolder: AppStateHolder
) : ViewModel() {
    private val _uiState = MutableStateFlow(DayUiState())
    val uiState = _uiState
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            DayUiState()
        )

    private val appPrefs = appStateHolder.appPreferencesStateHolder

    private val barCount = DAY_BAR_COUNT

    init {
        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {
            combine(
                appPrefs.appPrefFlow,
                _uiState.map { it.currentDate.value }.distinctUntilChanged()
            ) { appPrefs, dateRange ->
                Pair(appPrefs, dateRange)
            }.flatMapLatest { (appPrefs, dateRange) ->
                getConsumptionForDayFlowUseCase(dateRange).map { summary ->
                    Pair(appPrefs, summary)
                }
            }.collect { (appPrefs, dayConsumptionSummary) ->
                _uiState.update {
                    it.copy(
                        volumeUnit = appPrefs.volumeUnitSetting,
                        totalHydration = dayConsumptionSummary.totalHydroMl.toDisplayableVolume(
                            appPrefs.volumeUnitSetting
                        ),
                        records = dayConsumptionSummary.consumptions.map { consumption ->
                            DrinkRecordUi(
                                id = consumption.id,
                                time = consumption.timestamp.toDisplayableTime(),
                                totalHydration = consumption.volume.toDisplayableVolume(appPrefs.volumeUnitSetting)
                            )
                        },
                        achievementValue = if (dayConsumptionSummary.consumptions.isNotEmpty()) appPrefs.volumeUnitSetting.convertFromMilliliters(
                            (dayConsumptionSummary.totalMl / dayConsumptionSummary.consumptions.size)
                        ).toFloat()
                        else appPrefs.volumeUnitSetting.convertFromMilliliters(appPrefs.goalSetting)
                            .toFloat()
                    )
                }
                consumeBars(appPrefs.volumeUnitSetting)
            }
        }
    }

    fun onAction(action: DayScreenAction) {
        when (action) {
            is DayScreenAction.OnNextPeriodClick -> onNextPeriod()
            is DayScreenAction.OnPreviousPeriodClick -> onPreviousPeriod()
            is DayScreenAction.OnAddRecordClick -> onAddRecord()
            is DayScreenAction.OnRecordClick -> {}
        }
    }

    private fun onAddRecord() {
    }

    private fun onNextPeriod() {
        _uiState.update {
            it.copy(
                currentDate = it.currentDate.value.plusDays(1).toDisplayableDateFull()
            )
        }
    }

    private fun onPreviousPeriod() {
        _uiState.update {
            it.copy(
                currentDate = it.currentDate.value.minusDays(1).toDisplayableDateFull()
            )
        }
    }

    private fun consumeBars(volumeUnit: VolumeUnit) {
        val startDate = _uiState.value.currentDate.value
        val zone = ZoneId.systemDefault()
        val intervalMs = 1000L * 60 * 30
        val startOfDayMillis = startDate.atStartOfDay(zone).toInstant().toEpochMilli()

        val groupedByBarIndex =
            _uiState.value.records.groupBy { ((it.time.value - startOfDayMillis) / intervalMs).toInt() }

        val bars = (0 until barCount).map { barIndex ->
            val sum =
                groupedByBarIndex[barIndex]?.sumOf { it.totalHydration.value } ?: 0
            Bar(
                label = "$barIndex",
                value = volumeUnit.convertFromMilliliters(sum).toFloat()
            )
        }
        _uiState.update { it.copy(bars = bars) }
    }
}