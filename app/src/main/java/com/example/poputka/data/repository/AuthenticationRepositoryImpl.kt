package com.example.poputka.data.repository

import com.example.poputka.data.remote.data_source.AuthenticationDataSource
import com.example.poputka.data.remote.util.NetworkResult
import com.example.poputka.domain.repository.AuthenticationRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(private val authenticationDataSource: AuthenticationDataSource) :
    AuthenticationRepository {
    override suspend fun login(phoneNumber: String): Flow<NetworkResult<AuthResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun sendVerificationCode(phoneNumber: String): NetworkResult<String> =
        authenticationDataSource.sendVerificationCode(phoneNumber)
}