package com.example.poputka.feature_settings.domain.use_case

import com.example.poputka.core.domain.Result
import com.example.poputka.feature_settings.domain.model.errors.ValidationError
import javax.inject.Inject

class ValidateInputUseCase @Inject constructor() {

    operator fun invoke(newValue: String, maxValue: Int): Result<Int?, ValidationError> {
        if (newValue.isBlank()) return Result.Success(null)
        val num = newValue.toIntOrNull() ?: return Result.Error(ValidationError.INVALID_NUMBER)
        return when {
            num in 1..maxValue -> Result.Success(num)
            num > maxValue -> Result.Error(ValidationError.EXCEEDS_MAX)
            else -> Result.Error(ValidationError.TOO_LOW)
        }
    }
}

