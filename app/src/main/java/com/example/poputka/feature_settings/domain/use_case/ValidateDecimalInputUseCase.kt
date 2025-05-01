package com.example.poputka.feature_settings.domain.use_case

import com.example.poputka.core.domain.Result
import com.example.poputka.feature_settings.domain.model.errors.ValidationError
import javax.inject.Inject

class ValidateDecimalInputUseCase @Inject constructor() {
    operator fun invoke(
        newValue: String,
        maxValue: Double
    ): Result<String?, ValidationError> {
        if (newValue.isBlank()) return Result.Success(null)

        val normalized = newValue.replace(',', '.')
        val parts = normalized.split('.')
        if (parts.size > 2) return Result.Error(ValidationError.INVALID_NUMBER)
        if ((parts.getOrNull(1)?.length ?: 0) > MAX_DECIMAL_PLACES) {
            return Result.Error(ValidationError.INVALID_NUMBER)
        }

        val num = normalized.toDoubleOrNull() ?: return Result.Error(ValidationError.INVALID_NUMBER)
        return when {
            num <= 0.0 -> Result.Error(ValidationError.TOO_LOW)
            num > maxValue -> Result.Error(ValidationError.EXCEEDS_MAX)
            else -> Result.Success(newValue)
        }
    }

    companion object {
        private const val MAX_DECIMAL_PLACES = 2
    }
}