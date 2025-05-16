package com.example.poputka.feature_home.presentation.home_screen

import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.common.presentation.models.DisplayableInt
import com.example.poputka.common.presentation.models.mappers.toDisplayableVolume
import com.example.poputka.feature_home.presentation.home_screen.model.ConsumptionUi

data class HomeUiState(
    val currentProgress: Float = 0f,
    val goal: Float = 2000f,
    val progressPercent: Int = ((currentProgress / goal) * 100).toInt(),
    val unit: VolumeUnit = VolumeUnit.Milliliters,
    val totalToday: DisplayableInt = 0.toDisplayableVolume(unit),
    val totalHydro: DisplayableInt = 0.toDisplayableVolume(unit),
    val log: List<ConsumptionUi> = emptyList()
)