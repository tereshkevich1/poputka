package com.example.poputka.feature_tips.presentation.navigation

import kotlinx.serialization.Serializable

sealed class TipsDestination {
    @Serializable
    data object TipsNav : TipsDestination()
    @Serializable
    data object Tips : TipsDestination()
}