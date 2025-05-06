package com.example.poputka.common.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.poputka.common.domain.repository.AppDataStoreSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val PREFERENCES_NAME = "settings"

private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

class AppDataStoreSourceImpl @Inject constructor(
    private val context: Context
) : AppDataStoreSource {

    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun putInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun putLong(key: String, value: Long) {
        val preferencesKey = longPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun putFloat(key: String, value: Float) {
        val preferencesKey = floatPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun putDouble(key: String, value: Double) {
        val preferencesKey = doublePreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        val preferencesKey = booleanPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun putStringSet(key: String, value: Set<String>) {
        val preferencesKey = stringSetPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override fun longFlow(key: String): Flow<Long> {
        val preferencesKey = longPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: 0
        }
    }

    override fun intFlow(key: String): Flow<Int> {
        val preferencesKey = intPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: 0
        }
    }

    override fun floatFlow(key: String): Flow<Float> {
        val preferencesKey = floatPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: 1f
        }
    }

    override fun doubleFlow(key: String): Flow<Double> {
        val preferencesKey = doublePreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: 0.0
        }
    }

    override fun stringFlow(key: String): Flow<String> {
        val preferencesKey = stringPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: ""
        }
    }

    override fun booleanFlow(key: String): Flow<Boolean> {
        val preferencesKey = booleanPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: true
        }
    }

    override fun stringSetFlow(key: String): Flow<Set<String>> {
        val preferencesKey = stringSetPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: setOf()
        }
    }

    override suspend fun getStringValue(key: String, defaultValue: String): String {
        val preferencesKey = stringPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: defaultValue
        }.first()
    }

    override suspend fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        val preferencesKey = booleanPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: defaultValue
        }.first()
    }

    override suspend fun getIntValue(key: String, defaultValue: Int): Int {
        val preferencesKey = intPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: defaultValue
        }.first()
    }

    override suspend fun getLongValue(key: String, defaultValue: Long): Long {
        val preferencesKey = longPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: defaultValue
        }.first()
    }

    override suspend fun getFloatValue(key: String, defaultValue: Float): Float {
        val preferencesKey = floatPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: defaultValue
        }.first()
    }

    override suspend fun getDoubleValue(key: String, defaultValue: Double): Double {
        val preferencesKey = doublePreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: defaultValue
        }.first()
    }
}