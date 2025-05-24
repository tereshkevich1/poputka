package com.example.poputka.feature_journal.domain

import com.example.poputka.feature_home.domain.models.DailyConsumption
import com.example.poputka.feature_home.domain.repository.ConsumptionRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class GetDailyConsumptionFlowForPeriodUseCase @Inject constructor(private val consumptionRepository: ConsumptionRepository) {
    operator fun invoke(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<DailyConsumption>> {
        val zone = ZoneId.systemDefault()

        val startDateMillis = startDate.atStartOfDay(zone).toInstant().toEpochMilli()
        val endDateMillis = endDate.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli()

        return consumptionRepository.getDailyConsumptionFlowForPeriod(
            startDateMillis,
            endDateMillis
        )
    }
}