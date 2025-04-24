package com.example.poputka.feature_settings.domain

import com.example.poputka.feature_settings.domain.model.ActivityLevel
import com.example.poputka.feature_settings.domain.model.Gender

data class PersonalSettingsState(
    val birthday: Long = 1,
    val height: Int = 1,
    val weight: Int = 1,
    val gender: Gender = Gender.MAN,
    val activityLevel: ActivityLevel = ActivityLevel.MODERATE
)

