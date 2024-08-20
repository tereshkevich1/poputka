package com.example.poputka.presentation.authorization.auth_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poputka.data.remote.util.AuthFirebaseResult
import com.example.poputka.data.remote.util.NetworkResult
import com.example.poputka.domain.use_case.GetCredential
import com.example.poputka.domain.use_case.SendVerificationCode
import com.example.poputka.domain.use_case.SignInWithPhoneAuthCredential
import com.example.poputka.presentation.authorization.auth_screen.util.SendVerificationCodeState
import com.example.poputka.presentation.util.ScreenUIState
import com.google.firebase.auth.PhoneAuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sendVerificationCodeUseCase: SendVerificationCode,
    private val signInWithPhoneAuthCredential: SignInWithPhoneAuthCredential,
    private val getCredential: GetCredential
) : ViewModel() {

    private val _sendCodeState = MutableStateFlow<SendVerificationCodeState>(
        SendVerificationCodeState.Loading)
    val sendCodeState = _sendCodeState.asStateFlow()

    private val _signInState = MutableStateFlow<ScreenUIState<String>>(ScreenUIState.Loading)
    val signInState = _signInState.asStateFlow()

    var verificationId: String = ""

    fun sendVerificationCode(phoneNumber: String) {
        _sendCodeState.value = SendVerificationCodeState.Loading
        viewModelScope.launch {
            sendVerificationCodeUseCase(phoneNumber).collect { result ->
                when (result) {
                    is AuthFirebaseResult.PhoneNumberVerified -> {
                        _sendCodeState.value = SendVerificationCodeState.PhoneNumberVerified(result.credential)
                        signInWithPhone(result.credential)
                    }

                    is AuthFirebaseResult.Error -> {
                        _sendCodeState.value = SendVerificationCodeState.Error(result.message ?: "Unknown Error")
                        Log.d("AuthViewModel", "error: ${result.message}")
                    }

                    is AuthFirebaseResult.Exception -> {
                        _sendCodeState.value =
                            SendVerificationCodeState.Error(result.e.message ?: "Unknown Error")
                        Log.d("AuthViewModel", "exception: ${result.e.message}")
                    }

                    is AuthFirebaseResult.VerificationCodeSent -> {
                        _sendCodeState.value = SendVerificationCodeState.VerificationCodeSent(result.verificationId)
                        verificationId = result.verificationId
                        //Here we navigate to SmsVerificationScreen and manually sign in and put verification code(user input)
                        Log.d("AuthViewModel", "sendVerificationCode: ${result.verificationId}")
                    }
                }
            }
        }
    }

    private fun signInWithPhone(credential: PhoneAuthCredential) {
        _signInState.value = ScreenUIState.Loading
        viewModelScope.launch {
            signInWithPhoneAuthCredential.invoke(credential).collect{ result ->
                when(result){
                    is NetworkResult.Error -> {
                        _signInState.value = ScreenUIState.Error(result.message ?: "Unknown Error")
                        Log.d("AuthViewModel", "signIn error: ${result.message}")
                    }

                    is NetworkResult.Exception -> {
                        _signInState.value = ScreenUIState.Error(result.e.message ?: "Unknown Error")
                        Log.d("AuthViewModel", "sign in exception: ${result.e.message}")
                    }

                    is NetworkResult.Success -> {
                        _signInState.value = ScreenUIState.Success(result.data.user.toString())
                        Log.d("AuthViewModel", "sendVerificationCode: ${result.data.user}")
                    }
                }
            }
        }
    }

    fun getCredential(verificationCode: String, code: String) {
        _signInState.value = ScreenUIState.Loading
        viewModelScope.launch {
            getCredential.invoke(verificationCode, code).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        _signInState.value = ScreenUIState.Error(result.message ?: "Unknown Error")
                    }

                    is NetworkResult.Exception -> {
                        _signInState.value = ScreenUIState.Error(result.e.message ?: "Unknown Error")
                    }

                    is NetworkResult.Success -> {
                        Log.d("AuthViewModel", "credit - ${result.data}")
                        signInWithPhone(result.data)
                    }
                }
            }
        }
    }
}