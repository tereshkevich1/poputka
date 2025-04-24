package com.example.poputka.feature_settings.presentation.personal_settings_screen.models.errors

import com.example.poputka.R
import com.example.poputka.core.presentation.UiText
import com.example.poputka.feature_settings.domain.model.errors.ValidationError

fun ValidationError.asUiText(): UiText {
    return when (this) {
        ValidationError.INVALID_FORMAT ->
            UiText.StringResource(R.string.error_invalid_format)
        ValidationError.INVALID_NUMBER ->
            UiText.StringResource(R.string.error_invalid_number)
        ValidationError.TOO_LOW ->
            UiText.StringResource(R.string.error_too_low)
        ValidationError.EXCEEDS_MAX ->
            UiText.StringResource(R.string.error_exceeds_max)
    }
}