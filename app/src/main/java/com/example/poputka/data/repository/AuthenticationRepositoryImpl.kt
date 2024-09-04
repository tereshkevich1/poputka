package com.example.poputka.data.repository

import com.example.poputka.data.remote.data_source.AuthenticationDataSource
import com.example.poputka.data.remote.util.AuthFirebaseResult
import com.example.poputka.data.remote.util.NetworkResult
import com.example.poputka.domain.repository.AuthenticationRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(private val authenticationDataSource: AuthenticationDataSource) :
    AuthenticationRepository {
    override suspend fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential): NetworkResult<AuthResult> =
        authenticationDataSource.signInWithPhoneAuthCredential(credential)

    override suspend fun sendVerificationCode(phoneNumber: String): AuthFirebaseResult =
        authenticationDataSource.sendVerificationCode(phoneNumber)

    override suspend fun getCredential(
        verificationId: String,
        code: String
    ): NetworkResult<PhoneAuthCredential> =
        authenticationDataSource.getCredential(verificationId, code)
}