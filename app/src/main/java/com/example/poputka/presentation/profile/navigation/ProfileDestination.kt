package com.example.poputka.presentation.profile.navigation

import kotlinx.serialization.Serializable

sealed class ProfileDestination {
    @Serializable
    data object ProfileNav : ProfileDestination()
    @Serializable
    data object Profile : ProfileDestination()
}