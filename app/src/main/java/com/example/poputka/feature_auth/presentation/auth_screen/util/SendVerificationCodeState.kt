package com.example.poputka.feature_auth.presentation.auth_screen.util

import com.google.firebase.auth.PhoneAuthCredential

sealed class SendVerificationCodeState{
    data object Empty : SendVerificationCodeState()
    data object Loading : SendVerificationCodeState()
    data class PhoneNumberVerified(val credential: PhoneAuthCredential) : SendVerificationCodeState()
    data class VerificationCodeSent(val verificationId: String) : SendVerificationCodeState()
    data class Error(val message: String) : SendVerificationCodeState()
}

