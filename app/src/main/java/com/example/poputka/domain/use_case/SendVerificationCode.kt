package com.example.poputka.domain.use_case

import android.util.Log
import com.example.poputka.data.remote.util.AuthFirebaseResult
import com.example.poputka.domain.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class SendVerificationCode @Inject constructor(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(phoneNumber: String) = flow {
        when (val result = repository.sendVerificationCode(phoneNumber)) {
            is AuthFirebaseResult.VerificationCodeSent -> {
                Log.d("SendVerificationCodeUSECASE","VerificationCodeSent")
                emit(AuthFirebaseResult.VerificationCodeSent(result.verificationId))
            }
            is AuthFirebaseResult.Error -> {
                Log.d("SendVerificationCodeUSECASE","Error")
                emit(AuthFirebaseResult.Error(result.code, result.message))
            }
            is AuthFirebaseResult.Exception -> {
                Log.d("SendVerificationCodeUSECASE","Exception")
                emit(AuthFirebaseResult.Exception(result.e))
            }
            is AuthFirebaseResult.PhoneNumberVerified -> {
                Log.d("SendVerificationCodeUSECASE","PhoneNumberVerified")
                emit(AuthFirebaseResult.PhoneNumberVerified(result.credential))
            }
        }
    }.flowOn(Dispatchers.IO)
}