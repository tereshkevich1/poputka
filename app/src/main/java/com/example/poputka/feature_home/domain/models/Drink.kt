package com.example.poputka.feature_home.domain.models

data class Drink(
    val id: Int,
    val name: String,
    val type: String,
    val hydrationFactor: Float,
    val calories: Int
)