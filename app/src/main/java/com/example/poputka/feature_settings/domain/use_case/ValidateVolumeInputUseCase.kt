package com.example.poputka.feature_settings.domain.use_case

import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.core.domain.Result
import com.example.poputka.feature_settings.domain.model.errors.ValidationError
import javax.inject.Inject

class ValidateVolumeInputUseCase @Inject constructor(
    private val mlValidator: ValidateMillilitersInputUseCase,
    private val decimalValidator: ValidateDecimalInputUseCase
) {
    operator fun invoke(
        newValue: String,
        unit: VolumeUnit
    ): Result<String?, ValidationError> {
        return when (unit) {
            VolumeUnit.Milliliters -> {
                mlValidator(newValue, MAX_VOLUME_ML)
            }

            VolumeUnit.Liters, VolumeUnit.Ounces -> {
                val maxInSelectedUnit = unit.convertFromMilliliters(MAX_VOLUME_ML)
                decimalValidator(newValue, maxInSelectedUnit)
            }
        }
    }

    companion object {
        private const val MAX_VOLUME_ML = 5000
    }
}