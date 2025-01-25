package com.example.poputka.domain.use_case

import com.example.poputka.domain.util.RegexUtils.leadingZerosRegex
import com.example.poputka.domain.util.RegexUtils.nonZeroNumberRegex
import com.example.poputka.domain.util.RegexUtils.numberRegex
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