package com.example.poputka.presentation.home.navigation

import kotlinx.serialization.Serializable

sealed class HomeDestination {
    @Serializable
    data object HomeNav : HomeDestination()
    @Serializable
    data object Home : HomeDestination()
}