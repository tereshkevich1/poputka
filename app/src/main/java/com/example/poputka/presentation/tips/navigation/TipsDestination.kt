package com.example.poputka.presentation.tips.navigation

import kotlinx.serialization.Serializable

sealed class TipsDestination {
    @Serializable
    data object TipsNav : TipsDestination()
    @Serializable
    data object Tips : TipsDestination()
}