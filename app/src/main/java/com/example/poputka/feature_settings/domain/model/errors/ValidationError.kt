package com.example.poputka.feature_settings.domain.model.errors

import com.example.poputka.core.domain.Error

enum class ValidationError : Error {
    INVALID_FORMAT,
    INVALID_NUMBER,
    TOO_LOW,
    EXCEEDS_MAX
}