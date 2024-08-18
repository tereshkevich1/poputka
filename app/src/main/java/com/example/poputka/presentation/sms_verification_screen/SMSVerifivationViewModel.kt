package com.example.poputka.presentation.sms_verification_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SMSVerificationViewModel @Inject constructor() : ViewModel() {
    var counter by mutableIntStateOf(60)
    var isButtonEnabled by mutableStateOf(false)

    init {
        enableButton()
    }

    fun enableButton() {
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
        counter = 60
        isButtonEnabled = false
    }
}