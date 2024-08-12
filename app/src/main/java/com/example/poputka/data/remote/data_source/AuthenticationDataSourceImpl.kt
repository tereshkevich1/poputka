package com.example.poputka.data.remote.data_source

import android.util.Log
import com.example.poputka.data.remote.util.NetworkResult
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.PhoneAuthProvider.verifyPhoneNumber
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AuthenticationDataSourceImpl @Inject constructor(private val auth: FirebaseAuth) :
    AuthenticationDataSource {
    override suspend fun login(phoneNumber: String): Flow<NetworkResult<AuthResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun sendVerificationCode(phoneNumber: String): NetworkResult<String> {
        val result = suspendCancellableCoroutine<NetworkResult<String>> { continuation ->
            //.setActivity(currentActivity)
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS)
                .setCallbacks(object : OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        Log.d("TAG", "onVerificationCompleted:$credential")
                        if (continuation.isActive) {
                            continuation.resume(NetworkResult.Success(credential.smsCode.toString()))
                        }
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        Log.w("TAG", "onVerificationFailed", e)
                        if (continuation.isActive) {
                            continuation.resume(NetworkResult.Exception(e))
                        }
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: ForceResendingToken,
                    ) {
                        Log.d("TAG", "onCodeSent:$verificationId")
                        if (continuation.isActive) {
                            continuation.resume(NetworkResult.Success(verificationId))
                        }
                    }
                })
                .build()

            verifyPhoneNumber(options)

            continuation.invokeOnCancellation {
                // Handle the cancellation if necessary
            }
        }
        return result
    }
}
