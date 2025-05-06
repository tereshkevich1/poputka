package com.example.poputka.common.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppDataStoreSource {

    suspend fun putString(key: String, value: String)
    suspend fun putInt(key: String, value: Int)
    suspend fun putLong(key: String, value: Long)
    suspend fun putFloat(key: String, value: Float)
    suspend fun putDouble(key: String, value: Double)
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun putStringSet(key: String, value: Set<String>)

    fun longFlow(key: String): Flow<Long>
    fun intFlow(key: String): Flow<Int>
    fun floatFlow(key: String): Flow<Float>
    fun doubleFlow(key: String): Flow<Double>
    fun stringFlow(key: String): Flow<String>
    fun booleanFlow(key: String): Flow<Boolean>
    fun stringSetFlow(key: String): Flow<Set<String>>

    suspend fun getStringValue(key: String, defaultValue: String): String
    suspend fun getBooleanValue(key: String, defaultValue: Boolean): Boolean
    suspend fun getIntValue(key: String, defaultValue: Int): Int
    suspend fun getLongValue(key: String, defaultValue: Long): Long
    suspend fun getFloatValue(key: String, defaultValue: Float): Float
    suspend fun getDoubleValue(key: String, defaultValue: Double): Double
}