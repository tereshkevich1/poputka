package com.example.poputka.feature_home.presentation.home_screen.model

import com.example.poputka.common.presentation.DrinkCategory
import com.example.poputka.common.presentation.models.DisplayableInt
import com.example.poputka.common.presentation.models.DisplayableLong

data class ConsumptionUi(
    val id: Int = 0,
    val drinkType: DrinkCategory,
    val volume: DisplayableInt,
    val timestamp: DisplayableLong
)