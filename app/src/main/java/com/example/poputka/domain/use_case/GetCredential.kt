package com.example.poputka.domain.use_case

import com.example.poputka.data.remote.util.NetworkResult
import com.example.poputka.domain.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCredential @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke(verificationId: String, code: String) = flow {
        when (val result = authenticationRepository.getCredential(verificationId, code)) {
            is NetworkResult.Error -> {
                emit(NetworkResult.Error(result.code, result.message))
            }

            is NetworkResult.Exception -> {
                emit(NetworkResult.Exception(result.e))
            }

            is NetworkResult.Success -> {
                emit(NetworkResult.Success(result.data))
            }
        }
    }.flowOn(Dispatchers.IO)
}