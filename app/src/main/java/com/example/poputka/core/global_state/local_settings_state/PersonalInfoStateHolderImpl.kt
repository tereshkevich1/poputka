package com.example.poputka.core.global_state.local_settings_state

import com.example.poputka.core.domain.repository.AppDataStoreSource
import com.example.poputka.core.global_state.local_settings_state.mappers.toActivityLevelOrDefault
import com.example.poputka.core.global_state.local_settings_state.mappers.toGenderOrDefault
import com.example.poputka.feature_settings.domain.PersonalInfoStateHolder
import com.example.poputka.feature_settings.domain.PersonalSettingsState
import com.example.poputka.feature_settings.domain.model.ActivityLevel
import com.example.poputka.feature_settings.domain.model.Gender
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.math.roundToInt

class PersonalInfoStateHolderImpl @Inject constructor(private val appDataStoreSource: AppDataStoreSource) :
    PersonalInfoStateHolder {

    private val _personalInfoFlow = combine(
        appDataStoreSource.longFlow(BIRTHDAY_KEY),
        appDataStoreSource.intFlow(HEIGHT_KEY),
        appDataStoreSource.intFlow(WEIGHT_KEY),
        appDataStoreSource.stringFlow(GENDER_KEY),
        appDataStoreSource.stringFlow(ACTIVITY_LEVEL_KEY)
    ) { flows ->
        PersonalSettingsState(
            birthday = flows[0] as Long,
            height = flows[1] as Int,
            weight = flows[2] as Int,
            gender = (flows[3] as String?).toGenderOrDefault(),
            activityLevel = (flows[4] as String?).toActivityLevelOrDefault(),
        )
    }.stateIn(
        CoroutineScope(SupervisorJob() + Dispatchers.IO),
        SharingStarted.Eagerly,
        PersonalSettingsState()
    )

    override val personalInfoFlow: StateFlow<PersonalSettingsState>
        get() = _personalInfoFlow

    override suspend fun updateBirthday(birthday: Long) {
        appDataStoreSource.putLong(BIRTHDAY_KEY, birthday)
    }

    override suspend fun updateHeight(height: Int) {
        appDataStoreSource.putInt(HEIGHT_KEY, height)
    }

    override suspend fun updateWeight(weight: Float) {
        val weightInGrams = (weight * 1000).roundToInt()
        appDataStoreSource.putInt(WEIGHT_KEY, weightInGrams)
    }

    override suspend fun updateGender(gender: Gender) {
        appDataStoreSource.putString(GENDER_KEY, gender.name)
    }

    override suspend fun updateActivityLevel(activityLevel: ActivityLevel) {
        appDataStoreSource.putString(ACTIVITY_LEVEL_KEY, activityLevel.name)
    }

    companion object {
        const val BIRTHDAY_KEY = "pref_key_birthday"
        const val HEIGHT_KEY = "pref_key_height"
        const val WEIGHT_KEY = "pref_key_weight"
        const val GENDER_KEY = "pref_key_gender"
        const val ACTIVITY_LEVEL_KEY = "pref_key_activity_level"
    }
}

