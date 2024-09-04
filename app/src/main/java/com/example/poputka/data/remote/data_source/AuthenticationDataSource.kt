package com.example.poputka.data.remote.data_source

import com.example.poputka.data.remote.util.AuthFirebaseResult
import com.example.poputka.data.remote.util.NetworkResult
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential

interface AuthenticationDataSource {
    suspend fun sendVerificationCode(phoneNumber: String): AuthFirebaseResult
    suspend fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential): NetworkResult<AuthResult>
    suspend fun getCredential(verificationId: String, code: String): NetworkResult<PhoneAuthCredential>
}