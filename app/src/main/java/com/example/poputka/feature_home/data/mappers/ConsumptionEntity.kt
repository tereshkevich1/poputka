package com.example.poputka.feature_home.data.mappers

import com.example.poputka.feature_home.data.models.ConsumptionEntity
import com.example.poputka.feature_home.domain.models.Consumption

fun Consumption.toEntity(): ConsumptionEntity = ConsumptionEntity(
    id = id,
    drinkType = drinkType.name,
    volume = volume,
    timestamp = timestamp
)