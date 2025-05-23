package com.example.poputka.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {
    private val eventsChannel = Channel<T>()
    val events = eventsChannel.receiveAsFlow()

    fun sendEvent(event: T) = viewModelScope.launch {
        eventsChannel.send(event)
    }
}