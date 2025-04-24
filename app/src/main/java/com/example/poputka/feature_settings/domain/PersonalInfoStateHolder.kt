package com.example.poputka.feature_settings.domain

import com.example.poputka.feature_settings.domain.model.ActivityLevel
import com.example.poputka.feature_settings.domain.model.Gender
import kotlinx.coroutines.flow.StateFlow

interface PersonalInfoStateHolder {
    val personalInfoFlow: StateFlow<PersonalSettingsState>

    suspend fun updateBirthday(birthday: Long)

    suspend fun updateHeight(height: Int)

    suspend fun updateWeight(weight: Float)

    suspend fun updateGender(gender: Gender)

    suspend fun updateActivityLevel(activityLevel: ActivityLevel)
}