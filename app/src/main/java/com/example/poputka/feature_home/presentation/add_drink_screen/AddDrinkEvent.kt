package com.example.poputka.feature_home.presentation.add_drink_screen

sealed class AddDrinkEvent {
    data class ShowToast(val message: String) : AddDrinkEvent()
    data object NavigateBack : AddDrinkEvent()
}