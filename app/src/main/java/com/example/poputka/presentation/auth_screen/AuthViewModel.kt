package com.example.poputka.presentation.auth_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poputka.data.remote.util.AuthFirebaseResult
import com.example.poputka.domain.use_case.SendVerificationCode
import com.example.poputka.domain.use_case.SignInWithPhoneAuthCredential
import com.example.poputka.presentation.util.ScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sendVerificationCodeUseCase: SendVerificationCode,
    private val signInWithPhoneAuthCredential: SignInWithPhoneAuthCredential
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<ScreenUIState<Any>>(ScreenUIState.Empty)
    val uiState = _uiState.asStateFlow()

    fun sendVerificationCode(phoneNumber: String) {
        _uiState.value = ScreenUIState.Loading
        viewModelScope.launch {
            sendVerificationCodeUseCase(phoneNumber).collect { result ->
                when (result) {
                    is AuthFirebaseResult.PhoneNumberVerified -> {
                        _uiState.value = ScreenUIState.Success(result.credential)
                        signInWithPhoneAuthCredential(result.credential)
                        Log.d("AuthViewModel", "Phone number verified")
                        //Here we navigate to SmsVerificationScreen and automatically sign in and put verification code
                    }

                    is AuthFirebaseResult.Error -> {
                        _uiState.value = ScreenUIState.Error(result.message ?: "Unknown Error")
                        Log.d("AuthViewModel", "error: ${result.message}")
                    }

                    is AuthFirebaseResult.Exception -> {
                        _uiState.value =
                            ScreenUIState.Error(result.e.message ?: "Unknown Error")
                        Log.d("AuthViewModel", "exception: ${result.e.message}")
                    }

                    is AuthFirebaseResult.VerificationCodeSent -> {
                        _uiState.value = ScreenUIState.Success(result.verificationId)
                        //Here we navigate to SmsVerificationScreen and manually sign in and put verification code(user input)
                        Log.d("AuthViewModel", "sendVerificationCode: ${result.verificationId}")
                    }
                }
            }
        }
    }
}