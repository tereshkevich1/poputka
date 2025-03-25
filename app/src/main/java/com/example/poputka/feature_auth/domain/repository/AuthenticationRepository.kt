package com.example.poputka.feature_auth.domain.repository

import com.example.poputka.feature_auth.data.remote.util.AuthFirebaseResult
import com.example.poputka.feature_auth.data.remote.util.NetworkResult
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential

interface AuthenticationRepository {
    suspend fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential): NetworkResult<AuthResult>
    suspend fun sendVerificationCode(phoneNumber: String): AuthFirebaseResult
    suspend fun getCredential(verificationId: String, code: String): NetworkResult<PhoneAuthCredential>
}