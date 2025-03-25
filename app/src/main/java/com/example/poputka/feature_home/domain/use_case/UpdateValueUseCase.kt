package com.example.poputka.feature_home.domain.use_case

import com.example.poputka.feature_home.domain.util.RegexUtils.leadingZerosRegex
import com.example.poputka.feature_home.domain.util.RegexUtils.nonZeroNumberRegex
import com.example.poputka.feature_home.domain.util.RegexUtils.numberRegex
import javax.inject.Inject

class UpdateValueUseCase @Inject constructor() {
    operator fun invoke(newValue: String): String? {
        return when {
            nonZeroNumberRegex.matches(newValue) -> newValue
            numberRegex.matches(newValue) -> newValue.replaceFirst(leadingZerosRegex, "")
                .ifEmpty { "1" }

            else -> null
        }
    }
}