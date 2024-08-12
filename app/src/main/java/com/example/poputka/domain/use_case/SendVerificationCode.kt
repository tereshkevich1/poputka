package com.example.poputka.domain.use_case

import com.example.poputka.data.remote.util.NetworkResult
import com.example.poputka.domain.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class SendVerificationCode @Inject constructor(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(phoneNumber: String) = flow {
        when (val result = repository.sendVerificationCode(phoneNumber)) {

            is NetworkResult.Success -> {
                emit(NetworkResult.Success(result.data))
            }
            is NetworkResult.Error -> {
                emit(NetworkResult.Error(result.code, result.message))
            }
            is NetworkResult.Exception -> {
                emit(NetworkResult.Exception(result.e))
            }
        }
    }.flowOn(Dispatchers.IO)
}