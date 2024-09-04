package com.example.poputka.presentation.authorization.auth_screen

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poputka.R
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sendVerificationCodeUseCase: SendVerificationCode,
    private val signInWithPhoneAuthCredential: SignInWithPhoneAuthCredential,
    private val getCredential: GetCredential
) : ViewModel() {

    private val _sendCodeState = MutableStateFlow<SendVerificationCodeState>(
        SendVerificationCodeState.Empty
    )
    val sendCodeState = _sendCodeState.asStateFlow()

    private val _signInState = MutableStateFlow<ScreenUIState<String>>(ScreenUIState.Empty)
    val signInState = _signInState.asStateFlow()

    private val _messageIds: MutableStateFlow<List<Int>> = MutableStateFlow(emptyList())
    val messageIds = _messageIds.asStateFlow()

    private var verificationId: String = ""

    fun sendVerificationCode(phoneNumber: String) {
        _sendCodeState.value = SendVerificationCodeState.Loading
        viewModelScope.launch {
            sendVerificationCodeUseCase(phoneNumber).collect { result ->
                when (result) {
                    is AuthFirebaseResult.PhoneNumberVerified -> {
                        _sendCodeState.value =
                            SendVerificationCodeState.PhoneNumberVerified(result.credential)
                        signInWithPhone(result.credential)
                    }

                    is AuthFirebaseResult.Error -> {
                        _sendCodeState.value =
                            SendVerificationCodeState.Error(result.message ?: "Unknown Error")
                        setViewModelMessageShown(R.string.error_code_sending)
                    }

                    is AuthFirebaseResult.Exception -> {
                        _sendCodeState.value =
                            SendVerificationCodeState.Error(result.e.message ?: "Unknown Error")
                        setViewModelMessageShown(R.string.error_network)
                    }

                    is AuthFirebaseResult.VerificationCodeSent -> {
                        _sendCodeState.value =
                            SendVerificationCodeState.VerificationCodeSent(result.verificationId)
                        verificationId = result.verificationId
                    }
                }
            }
        }
    }

    private fun signInWithPhone(credential: PhoneAuthCredential) {
        _signInState.value = ScreenUIState.Loading
        viewModelScope.launch {
            signInWithPhoneAuthCredential.invoke(credential).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        _signInState.value = ScreenUIState.Error(result.message ?: "Unknown Error")
                    }

                    is NetworkResult.Exception -> {
                        _signInState.value =
                            ScreenUIState.Error(result.e.message ?: "Unknown Error")
                    }

                    is NetworkResult.Success -> {
                        _signInState.value = ScreenUIState.Success(result.data.user.toString())
                    }
                }
            }
        }
    }

    fun getCredential(code: String) {
        _signInState.value = ScreenUIState.Loading
        viewModelScope.launch {
            getCredential.invoke(verificationId, code).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        _signInState.value = ScreenUIState.Error(result.message ?: "Unknown Error")
                    }

                    is NetworkResult.Exception -> {
                        _signInState.value =
                            ScreenUIState.Error(result.e.message ?: "Unknown Error")
                    }

                    is NetworkResult.Success -> {
                        signInWithPhone(result.data)
                    }
                }
            }
        }
    }

    fun setMessageShown(@StringRes messageId: Int) {
        _messageIds.update { currentMessageIds ->
            currentMessageIds.filter { it != messageId }
        }
    }

    fun setViewModelMessageShown(@StringRes messageId: Int) {
        _messageIds.update { currentMessageIds ->
            currentMessageIds + messageId
        }
    }
}