package com.example.poputka.feature_home.domain.models

data class DayConsumptionSummary(
    val totalMl: Int,
    val totalHydroMl: Int,
    val consumptions: List<Consumption>
)