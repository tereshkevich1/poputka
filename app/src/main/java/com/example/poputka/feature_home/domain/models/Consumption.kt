package com.example.poputka.feature_home.domain.models

import com.example.poputka.common.presentation.DrinkCategory

data class Consumption(
    val id: Int = 0,
    val drinkType: DrinkCategory,
    val volume: Int,
    val timestamp: Long
)