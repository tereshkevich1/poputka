package com.example.poputka.feature_journal.domain

import android.util.Log
import com.example.poputka.feature_home.domain.models.DayConsumptionSummary
import com.example.poputka.feature_home.domain.repository.ConsumptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class GetConsumptionForDayFlowUseCase @Inject constructor(
    private val repository: ConsumptionRepository
) {
    operator fun invoke(startDate: LocalDate): Flow<DayConsumptionSummary> {
        val zone = ZoneId.systemDefault()

        val startOfDay = startDate.atStartOfDay(zone).toInstant().toEpochMilli()
        val endOfDay = startDate.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli()

        Log.d("FlowDebug", "Flow STARTED for date = $startOfDay $endOfDay")
        return repository.getForPeriodFlow(startOfDay, endOfDay)
            .map { list ->
                val totalMl = list.sumOf { it.volume }
                val totalHydroMl = list.sumOf { it.volume * it.drinkType.hydration / 100 }
                DayConsumptionSummary(
                    totalMl = totalMl,
                    totalHydroMl = totalHydroMl,
                    consumptions = list
                )
            }.onStart {
                Log.d("FlowDebug", "Flow STARTED for date = $startDate")
            }
            .onCompletion { cause ->
                if (cause == null) {
                    Log.d("FlowDebug", "Flow COMPLETED normally for date = $startDate")
                } else {
                    Log.d("FlowDebug", "Flow CANCELLED for date = $startDate, cause = $cause")
                }
            }
    }
}