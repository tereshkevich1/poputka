package com.example.poputka.feature_home.domain.repository

import com.example.poputka.feature_home.domain.models.Consumption
import kotlinx.coroutines.flow.Flow

interface ConsumptionRepository {
    suspend fun getForPeriod(start: Long, end: Long): List<Consumption>
    fun getForPeriodFlow(startMillis: Long, endMillis: Long): Flow<List<Consumption>>
    suspend fun getTotalVolume(): Int
    suspend fun getTotalVolumeForPeriod(start: Long, end: Long): Int
    suspend fun upsert(consumption: Consumption)
}