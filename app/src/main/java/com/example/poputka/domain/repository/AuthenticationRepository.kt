package com.example.poputka.domain.repository

import com.example.poputka.data.remote.util.NetworkResult
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    suspend fun login(phoneNumber: String): Flow<NetworkResult<AuthResult>>
    suspend fun sendVerificationCode(phoneNumber: String): NetworkResult<String>
}