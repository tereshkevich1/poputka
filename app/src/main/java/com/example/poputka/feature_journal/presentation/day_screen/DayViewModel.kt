package com.example.poputka.feature_journal.presentation.day_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poputka.common.domain.AppStateHolder
import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.common.presentation.models.mappers.toDisplayableDateFull
import com.example.poputka.common.presentation.models.mappers.toDisplayableTime
import com.example.poputka.common.presentation.models.mappers.toDisplayableVolume
import com.example.poputka.feature_journal.domain.GetConsumptionForDayFlowUseCase
import com.example.poputka.feature_journal.presentation.charts.bar_chart.Bar
import com.example.poputka.feature_journal.presentation.charts.common.ChartModeConstants.DAY_BAR_COUNT
import com.example.poputka.feature_journal.presentation.journal_screen.DrinkRecordUi
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
class DayViewModel @Inject constructor(
    private val getConsumptionForDayFlowUseCase: GetConsumptionForDayFlowUseCase,
    appStateHolder: AppStateHolder
) : ViewModel() {
    private val _dayState = MutableStateFlow(DayState())
    val dayState = _dayState
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            DayState()
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
                _dayState.map { it.currentDate.value }.distinctUntilChanged()
            ) { appPrefs, dateRange ->
                Pair(appPrefs, dateRange)
            }.flatMapLatest { (appPrefs, dateRange) ->
                getConsumptionForDayFlowUseCase(dateRange).map { summary ->
                    Pair(appPrefs, summary)
                }
            }.collect { (appPrefs, dayConsumptionSummary) ->
                _dayState.update {
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
        _dayState.update {
            it.copy(
                currentDate = it.currentDate.value.plusDays(1).toDisplayableDateFull()
            )
        }
    }

    private fun onPreviousPeriod() {
        _dayState.update {
            it.copy(
                currentDate = it.currentDate.value.minusDays(1).toDisplayableDateFull()
            )
        }
    }

    private fun consumeBars(volumeUnit: VolumeUnit) {
        val startDate = _dayState.value.currentDate.value
        val zone = ZoneId.systemDefault()
        val intervalMs = 1000L * 60 * 30
        val startOfDayMillis = startDate.atStartOfDay(zone).toInstant().toEpochMilli()

        val groupedByBarIndex =
            _dayState.value.records.groupBy { ((it.time.value - startOfDayMillis) / intervalMs).toInt() }

        val bars = (0 until barCount).map { barIndex ->
            val sum =
                groupedByBarIndex[barIndex]?.sumOf { it.totalHydration.value } ?: 0
            Bar(
                label = "$barIndex",
                value = volumeUnit.convertFromMilliliters(sum).toFloat()
            )
        }
        _dayState.update { it.copy(bars = bars) }
    }
}


