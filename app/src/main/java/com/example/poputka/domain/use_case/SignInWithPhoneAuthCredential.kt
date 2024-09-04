package com.example.poputka.domain.use_case

import com.example.poputka.data.remote.util.NetworkResult
import com.example.poputka.domain.repository.AuthenticationRepository
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInWithPhoneAuthCredential @Inject constructor(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(credential: PhoneAuthCredential) =
        flow {
            when (val result = repository.signInWithPhoneAuthCredential(credential)) {
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
        }
}