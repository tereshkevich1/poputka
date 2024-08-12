package com.example.poputka.data.remote.data_source

import com.example.poputka.data.remote.util.NetworkResult
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthenticationDataSource {
    suspend fun login(phoneNumber: String): Flow<NetworkResult<AuthResult>>
    suspend fun sendVerificationCode(phoneNumber: String): NetworkResult<String>
}