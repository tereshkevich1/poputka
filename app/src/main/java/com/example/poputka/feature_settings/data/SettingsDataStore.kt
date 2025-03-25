package com.example.poputka.feature_settings.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsDataStore @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    fun getStringFlow(key: String): Flow<String?> = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(key)]
    }

    fun getIntFlow(key: String): Flow<Int?> = dataStore.data.map { preferences ->
        preferences[intPreferencesKey(key)]
    }

    fun getBooleanFlow(key: String): Flow<Boolean?> = dataStore.data.map { preferences ->
        preferences[booleanPreferencesKey(key)]
    }

    suspend fun putString(value: String, key: String){
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    suspend fun putInt(value: Int, key: String){
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }

    suspend fun putBoolean(value: Boolean, key: String){
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }
}