package com.example.poputka.feature_home.domain.models

data class Nutrient(
    val id: Int,
    val drinkId: Int,
    val nutrientName: String,
    val amountPer100ml: Float
)