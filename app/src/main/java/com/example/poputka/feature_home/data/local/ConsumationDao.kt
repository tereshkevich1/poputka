package com.example.poputka.feature_home.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.poputka.feature_home.data.models.ConsumptionEntity
import com.example.poputka.feature_home.data.models.DailyConsumptionDao
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsumptionDao {

    @Query("SELECT * FROM consumption ORDER BY timestamp DESC")
    suspend fun getAllConsumptions(): List<ConsumptionEntity>

    @Query("SELECT * FROM consumption WHERE timestamp BETWEEN :startMillis AND :endMillis ORDER BY timestamp DESC")
    suspend fun getConsumptionsForPeriod(startMillis: Long, endMillis: Long): List<ConsumptionEntity>

    @Query("SELECT * FROM consumption WHERE timestamp BETWEEN :startMillis AND :endMillis ORDER BY timestamp DESC")
    fun getConsumptionsForPeriodFlow(startMillis: Long, endMillis: Long): Flow<List<ConsumptionEntity>>

    @Query(
        """
        SELECT strftime('%Y-%m-%d', (timestamp / 1000) + :zoneOffsetSeconds, 'unixepoch') AS date,
               SUM(hydration_volume) AS totalVolume
        FROM consumption
        WHERE timestamp BETWEEN :startMillis AND :endMillis
        GROUP BY date
        ORDER BY date
    """
    )
    fun getDailyConsumptionFlowForPeriod(
        startMillis: Long,
        endMillis: Long,
        zoneOffsetSeconds: Int
    ): Flow<List<DailyConsumptionDao>>

    @Query("SELECT SUM(volume) FROM consumption WHERE timestamp BETWEEN :startTime AND :endTime")
    suspend fun getTotalVolumeBetween(startTime: Long, endTime: Long): Int?

    @Query("SELECT SUM(volume) FROM consumption")
    suspend fun getTotalVolume(): Int?

    @Upsert
    suspend fun upsertConsumption(consumption: ConsumptionEntity)

    @Upsert
    fun upsertConsumption(consumptions: List<ConsumptionEntity>)

    @Query("DELETE FROM consumption WHERE id = :id")
    suspend fun deleteById(id: Int)
}

