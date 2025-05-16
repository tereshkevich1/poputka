package com.example.poputka.feature_home.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.poputka.feature_home.data.models.ConsumptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsumptionDao {

    @Query("SELECT * FROM consumption ORDER BY timestamp DESC")
    suspend fun getAllConsumptions(): List<ConsumptionEntity>

    @Query("SELECT * FROM consumption WHERE timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp DESC")
    suspend fun getConsumptionsBetween(startTime: Long, endTime: Long): List<ConsumptionEntity>

    @Query("SELECT * FROM consumption WHERE timestamp BETWEEN :start AND :end ORDER BY timestamp DESC")
    fun getForPeriodFlow(start: Long, end: Long): Flow<List<ConsumptionEntity>>

    // По drinkType
    @Query("SELECT * FROM consumption WHERE drink_type = :drinkType ORDER BY timestamp DESC")
    suspend fun getConsumptionsByType(drinkType: String): List<ConsumptionEntity>

    @Query("SELECT SUM(volume) FROM consumption")
    suspend fun getTotalVolume(): Int?

    @Query("SELECT SUM(volume) FROM consumption WHERE timestamp BETWEEN :startTime AND :endTime")
    suspend fun getTotalVolumeBetween(startTime: Long, endTime: Long): Int?

    @Upsert
    suspend fun upsert(consumption: ConsumptionEntity)

    @Upsert
    fun upsertAll(consumptions: List<ConsumptionEntity>)

    @Query("DELETE FROM consumption WHERE id = :id")
    suspend fun deleteById(id: Int)
}