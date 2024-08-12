package com.example.poputka.presentation.util

sealed class ScreenUIState<out T> {
    data object Empty : ScreenUIState<Nothing>()
    data object Loading : ScreenUIState<Nothing>()
    data class Success<out T>(val data: T) : ScreenUIState<T>()
    data class Error(val message: String) : ScreenUIState<Nothing>()
}