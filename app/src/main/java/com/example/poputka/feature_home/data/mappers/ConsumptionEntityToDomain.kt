package com.example.poputka.feature_home.data.mappers

import com.example.poputka.feature_home.data.models.ConsumptionEntity
import com.example.poputka.feature_home.domain.mappers.toDrinkCategoryOrDefault
import com.example.poputka.feature_home.domain.models.Consumption

fun ConsumptionEntity.toDomain(): Consumption = Consumption(
    id = id,
    drinkType = drinkType.toDrinkCategoryOrDefault(),
    volume = volume,
    timestamp = timestamp
)