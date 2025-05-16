package com.example.poputka.feature_home.presentation.home_screen

import com.example.poputka.feature_home.presentation.home_screen.model.ConsumptionUi

sealed interface HomeAction {
    data object OnRefresh : HomeAction
    data class OnDrinkClick(val consumption: ConsumptionUi) : HomeAction
}