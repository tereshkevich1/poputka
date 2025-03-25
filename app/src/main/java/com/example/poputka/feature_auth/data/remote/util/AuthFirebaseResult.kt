package com.example.poputka.feature_auth.data.remote.util

import com.google.firebase.auth.PhoneAuthCredential

sealed class AuthFirebaseResult {
    data class PhoneNumberVerified(val credential: PhoneAuthCredential) : AuthFirebaseResult()
    data class VerificationCodeSent(val verificationId: String) : AuthFirebaseResult()
    data class Error(val code: Int, val message: String?) : AuthFirebaseResult()
    data class Exception(val e: Throwable) : AuthFirebaseResult()
}