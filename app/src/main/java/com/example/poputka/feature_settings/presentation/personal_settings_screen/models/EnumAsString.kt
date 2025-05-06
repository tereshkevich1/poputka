package com.example.poputka.feature_settings.presentation.personal_settings_screen.models

import com.example.poputka.R
import com.example.poputka.core.presentation.UiText
import com.example.poputka.feature_settings.domain.model.ActivityLevel
import com.example.poputka.feature_settings.domain.model.Gender

fun Gender.asUiText(): UiText = when (this) {
    Gender.MAN -> UiText.StringResource(R.string.man)
    Gender.WOMAN -> UiText.StringResource(R.string.woman)
}

fun ActivityLevel.asUiText(): UiText = when (this) {
    ActivityLevel.LOW -> UiText.StringResource(R.string.activity_low)
    ActivityLevel.MODERATE -> UiText.StringResource(R.string.activity_moderately)
    ActivityLevel.INTENSIVE -> UiText.StringResource(R.string.activity_intensive)
    ActivityLevel.VERY_LOW -> UiText.StringResource(R.string.activity_very_low)
    ActivityLevel.VERY_INTENSIVE -> UiText.StringResource(R.string.activity_very_intensive)
}

