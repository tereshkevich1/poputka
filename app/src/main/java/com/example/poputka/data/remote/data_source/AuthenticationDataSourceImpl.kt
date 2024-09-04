package com.example.poputka.data.remote.data_source

import android.content.ContentValues.TAG
import android.util.Log
import com.example.poputka.data.remote.util.AuthFirebaseResult
import com.example.poputka.data.remote.util.NetworkResult
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.PhoneAuthProvider.verifyPhoneNumber
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AuthenticationDataSourceImpl @Inject constructor(private val auth: FirebaseAuth) :
    AuthenticationDataSource {

    override suspend fun sendVerificationCode(phoneNumber: String): AuthFirebaseResult =
        suspendCancellableCoroutine { continuation ->
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS)
                .setCallbacks(object : OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        Log.d(TAG, "onVerificationCompleted:$credential")

                        if (continuation.isActive) {
                            continuation.resume(AuthFirebaseResult.PhoneNumberVerified(credential) )
                        }
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        Log.w(TAG, "onVerificationFailed", e)
                        if (continuation.isActive) {
                            continuation.resume(AuthFirebaseResult.Exception(e))
                        }
                    }

                     override fun onCodeSent(
                         verificationId: String,
                         token: PhoneAuthProvider.ForceResendingToken,
                     ) {
                         Log.d("t", "onCodeSent:$verificationId")
                         if (continuation.isActive) {
                             continuation.resume(AuthFirebaseResult.VerificationCodeSent(verificationId))
                         }
                     }
                })
                .build()

            verifyPhoneNumber(options)

            continuation.invokeOnCancellation {
                // Handle the cancellation if necessary
            }
        }


    override suspend fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential): NetworkResult<AuthResult> =
        suspendCancellableCoroutine { continuation ->
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithCredential:success")

                        val user = task.result?.user

                        Log.d("user", user.toString())
                        continuation.resume(NetworkResult.Success(task.result!!))
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w("TAG", "signInWithCredential:failure", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            continuation.resume(
                                NetworkResult.Exception(
                                    (task.exception as FirebaseAuthInvalidCredentialsException).cause
                                        ?: Exception()
                                )
                            )
                        }
                    }
                }
        }

    override suspend fun getCredential(verificationId: String, code: String): NetworkResult<PhoneAuthCredential> {
        return try {
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            NetworkResult.Success(credential)
        } catch (e: Exception) {
            NetworkResult.Exception(e)
        }
    }
}
