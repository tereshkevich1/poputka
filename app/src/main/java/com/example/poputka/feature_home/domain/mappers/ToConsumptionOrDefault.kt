package com.example.poputka.feature_home.domain.mappers

import com.example.poputka.common.presentation.DrinkCategory

fun String?.toDrinkCategoryOrDefault(): DrinkCategory {
    return try {
        if (this != null) DrinkCategory.valueOf(this) else DrinkCategory.Water
    } catch (e: IllegalArgumentException) {
        DrinkCategory.Water
    }
}