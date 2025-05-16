package com.example.poputka.feature_home.presentation.home_screen.model

import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.common.presentation.models.mappers.toDisplayableTime
import com.example.poputka.common.presentation.models.mappers.toDisplayableVolume
import com.example.poputka.feature_home.domain.models.Consumption

fun Consumption.toUi(unit: VolumeUnit): ConsumptionUi =
    ConsumptionUi(
        id = id,
        drinkType = drinkType,
        volume = volume.toDisplayableVolume(unit),
        timestamp = timestamp.toDisplayableTime()
    )