package com.example.poputka.presentation.auth_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poputka.data.remote.util.NetworkResult
import com.example.poputka.domain.use_case.SendVerificationCode
import com.example.poputka.presentation.util.ScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val sendVerificationCodeUseCase: SendVerificationCode): ViewModel() {
    private val _uiState =
        MutableStateFlow<ScreenUIState<String>>(ScreenUIState.Empty)
    val uiState = _uiState.asStateFlow()

    fun sendVerificationCode(phoneNumber: String){
        _uiState.value = ScreenUIState.Loading
        viewModelScope.launch {
            sendVerificationCodeUseCase(phoneNumber).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.value = ScreenUIState.Success(result.data)
                    }

                    is NetworkResult.Error -> {
                        _uiState.value = ScreenUIState.Error(result.message ?: "Unknown Error")
                    }

                    is NetworkResult.Exception -> {
                        _uiState.value =
                            ScreenUIState.Error(result.e.message ?: "Unknown Error")
                    }
                }
            }
        }
    }
}