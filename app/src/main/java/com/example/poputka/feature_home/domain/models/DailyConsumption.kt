package com.example.poputka.feature_home.domain.models

import java.time.LocalDate

data class DailyConsumption(
    val totalHydrationVolume: Int,
    val date: LocalDate
)