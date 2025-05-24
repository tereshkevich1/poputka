package com.example.poputka.feature_home.data.repository

import com.example.poputka.feature_home.data.local.ConsumptionDao
import com.example.poputka.feature_home.domain.models.DailyConsumption
import com.example.poputka.feature_home.data.mappers.toDomain
import com.example.poputka.feature_home.data.mappers.toEntity
import com.example.poputka.feature_home.domain.models.Consumption
import com.example.poputka.feature_home.domain.repository.ConsumptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

class ConsumptionRepositoryImpl @Inject constructor(
    private val dao: ConsumptionDao
) : ConsumptionRepository {

    override suspend fun getForPeriod(start: Long, end: Long): List<Consumption> =
        dao.getConsumptionsForPeriod(start, end).map { it.toDomain() }


    override fun getForPeriodFlow(
        startMillis: Long,
        endMillis: Long
    ): Flow<List<Consumption>> =
        dao.getConsumptionsForPeriodFlow(startMillis, endMillis).map { consumption ->
            consumption.map {
                it.toDomain()
            }
        }


    override suspend fun getTotalVolume(): Int =
        dao.getTotalVolume() ?: 0


    override suspend fun getTotalVolumeForPeriod(startMillis: Long, endMillis: Long): Int =
        dao.getTotalVolumeBetween(startMillis, endMillis) ?: 0


    override fun getDailyConsumptionFlowForPeriod(
        startMillis: Long,
        endMillis: Long
    ): Flow<List<DailyConsumption>> {

        val zoneOffsetSeconds = ZoneId.systemDefault()
            .rules
            .getOffset(Instant.now())
            .totalSeconds

        return dao.getDailyConsumptionFlowForPeriod(startMillis, endMillis, zoneOffsetSeconds)
            .map { dailyConsumptionList ->
                dailyConsumptionList.map {
                    it.toDomain()
                }
            }
    }


    override suspend fun upsert(consumption: Consumption) {
        dao.upsertConsumption(consumption.toEntity())
    }
}
