package com.example.poputka.feature_home.presentation.add_drink_screen

import com.example.poputka.common.presentation.DrinkCategory
import com.example.poputka.common.presentation.models.DisplayableLong
import com.example.poputka.common.presentation.models.mappers.toDisplayableTime
import com.example.poputka.common.presentation.models.mappers.toSmartDisplayableDate

data class AddDrinkUiState(
    var time: DisplayableLong = System.currentTimeMillis().toDisplayableTime(),
    var date: DisplayableLong = System.currentTimeMillis().toSmartDisplayableDate(),
    var drinkCategory: DrinkCategory = DrinkCategory.Water,
    var volume: String = "300",
    var currentVolumeFloat: Float = 300f,
    var maxVolumeFloat: Float = 1000f,
    var volumeSliderRatio: Float = 0.3f
)