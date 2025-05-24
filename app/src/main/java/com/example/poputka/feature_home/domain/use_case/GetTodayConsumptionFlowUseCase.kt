package com.example.poputka.feature_home.domain.use_case

import com.example.poputka.feature_home.domain.models.DayConsumptionSummary
import com.example.poputka.feature_home.domain.repository.ConsumptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class GetTodayConsumptionFlowUseCase @Inject constructor(
    private val repository: ConsumptionRepository
) {
    operator fun invoke(): Flow<DayConsumptionSummary> {
        val now = LocalDate.now()
        val zone = ZoneId.systemDefault()

        val startOfDay = now.atStartOfDay(zone).toInstant().toEpochMilli()
        val endOfDay = now.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli()

        return repository.getForPeriodFlow(startOfDay, endOfDay)
            .map { list ->
                val totalMl = list.sumOf { it.volume }
                val totalHydroMl = list.sumOf { it.hydrationVolume }
                DayConsumptionSummary(
                    totalMl = totalMl,
                    totalHydroMl = totalHydroMl,
                    consumptions = list
                )
            }
    }
}