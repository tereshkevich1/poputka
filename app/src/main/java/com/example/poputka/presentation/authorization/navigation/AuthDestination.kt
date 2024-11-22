package com.example.poputka.presentation.authorization.navigation

import kotlinx.serialization.Serializable

sealed class AuthDestination {
    @Serializable
    data class Login(val phoneNumber: String) : AuthDestination()
    @Serializable
    data object SmsVerification : AuthDestination()
    @Serializable
    data object AutNavigationItem : AuthDestination()
}