package com.example.poputka.feature_home.presentation.navigation

import kotlinx.serialization.Serializable

sealed class HomeDestination {
    @Serializable
    data object HomeNav : HomeDestination()
    @Serializable
    data object Home : HomeDestination()
}