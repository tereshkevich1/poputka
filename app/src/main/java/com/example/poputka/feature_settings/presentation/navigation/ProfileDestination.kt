package com.example.poputka.feature_settings.presentation.navigation

import kotlinx.serialization.Serializable

sealed class ProfileDestination {
    @Serializable
    data object ProfileNav : ProfileDestination()

    @Serializable
    data object Profile : ProfileDestination()

    @Serializable
    data object PersonalInfo : ProfileDestination()

    @Serializable
    data object NotificationSettings : ProfileDestination()
}