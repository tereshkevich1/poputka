package com.example.poputka.core.global_state.local_settings_state.mappers

import com.example.poputka.feature_settings.domain.model.Gender

fun String?.toGenderOrDefault(): Gender {
    return try {
        if (this != null) Gender.valueOf(this) else Gender.MAN
    } catch (e: IllegalArgumentException) {
        Gender.MAN
    }
}