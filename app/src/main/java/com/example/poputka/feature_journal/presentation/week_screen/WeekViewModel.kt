package com.example.poputka.feature_journal.presentation.week_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poputka.common.domain.AppStateHolder
import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.common.presentation.models.mappers.formatDateRange
import com.example.poputka.feature_journal.domain.GetDailyConsumptionFlowForPeriodUseCase
import com.example.poputka.feature_journal.presentation.charts.bar_chart.Bar
import com.example.poputka.feature_journal.presentation.charts.common.ChartModeConstants
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
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class WeekViewModel @Inject constructor(
    private val getDailyConsumptionFlowForPeriodUseCase: GetDailyConsumptionFlowForPeriodUseCase,
    appStateHolder: AppStateHolder
) :
    ViewModel() {
    private val _weekState = MutableStateFlow(WeekState())
    val weekState = _weekState.stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000),
        WeekState()
    )

    private val appPrefs = appStateHolder.appPreferencesStateHolder

    private val barCount = ChartModeConstants.WEEK_BAR_COUNT

    init {
        observeDate()
    }

    private fun observeDate() {
        viewModelScope.launch {
            combine(
                appPrefs.appPrefFlow,
                _weekState.map { Pair(it.startDate, it.endDate) }.distinctUntilChanged()
            ) { appPrefs, (startDate, endDate) ->
                Triple(appPrefs, startDate, endDate)
            }.flatMapLatest { (appPrefs, startDate, endDate) ->
                getDailyConsumptionFlowForPeriodUseCase(startDate, endDate).map {
                    Pair(appPrefs, it)
                }
            }.collect { (appPrefs, dailyConsumptionList) ->
                _weekState.update {
                    Log.d("dailyConsumptionList", dailyConsumptionList.toString())
                    val records = dailyConsumptionList.map {
                        it.toUi(appPrefs.volumeUnitSetting)
                    }
                    val bars =
                        generateBars(volumeUnit = appPrefs.volumeUnitSetting, records = records)
                    it.copy(
                        volumeUnit = appPrefs.volumeUnitSetting,
                        records = records,
                        bars = bars
                    )
                }
            }
        }
    }

    fun onAction(action: WeekScreenAction) {
        when (action) {
            is WeekScreenAction.OnNextPeriodClick -> onNextPeriod()
            is WeekScreenAction.OnPreviousPeriodClick -> onPreviousPeriod()
            is WeekScreenAction.OnAddRecordClick -> onAddRecord()
            is WeekScreenAction.OnRecordClick -> {}
        }
    }

    private fun onAddRecord() {
    }

    private fun onNextPeriod() {
        _weekState.update {
            val startDate = it.startDate.plusWeeks(1)
            val endDate = it.endDate.plusWeeks(1)
            it.copy(
                startDate = startDate,
                endDate = endDate,
                formattedDateRange = formatDateRange(startDate, endDate)
            )
        }
    }

    private fun onPreviousPeriod() {
        _weekState.update {
            val startDate = it.startDate.minusWeeks(1)
            val endDate = it.endDate.minusWeeks(1)
            it.copy(
                startDate = startDate,
                endDate = endDate,
                formattedDateRange = formatDateRange(startDate, endDate)
            )
        }
    }

    private fun generateBars(volumeUnit: VolumeUnit, records: List<DailyConsumptionUi>): List<Bar> {
        val state = _weekState.value
        val recordsMap = records.associateBy { it.date.value }

        return (0 until barCount).map { barIndex ->
            val currentDate = state.startDate.plusDays(barIndex.toLong())
            val volumeMl = recordsMap[currentDate]?.totalVolume?.value ?: 0

            Bar(
                label = "$barIndex",
                value = volumeUnit.convertFromMilliliters(volumeMl).toFloat()
            )
        }
    }
}