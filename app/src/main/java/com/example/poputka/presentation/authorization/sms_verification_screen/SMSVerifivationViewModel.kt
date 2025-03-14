package com.example.poputka.presentation.authorization.sms_verification_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poputka.presentation.util.constants.SMS_CODE_RESEND_INTERVAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SMSVerificationViewModel @Inject constructor() : ViewModel() {
    var counter by mutableIntStateOf(SMS_CODE_RESEND_INTERVAL)
    var isButtonEnabled by mutableStateOf(false)

    init {
        enableButton()
    }

    private fun enableButton() {
        viewModelScope.launch {
            while (counter > 0) {
                delay(1000L)
                Log.d("counter", counter.toString())
                counter--
            }
            isButtonEnabled = true
        }
    }

    fun disableButton() {
        counter = SMS_CODE_RESEND_INTERVAL
        isButtonEnabled = false
        enableButton()
    }
}