@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka.feature_home.presentation.add_drink_screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.common.presentation.DrinkCategory

sealed class AddDrinkAction {
    data object OnBackClick : AddDrinkAction()
    data class OnAddClick(val volumeUnit: VolumeUnit) : AddDrinkAction()
    data class OnVolumeChange(val newVolume: String) : AddDrinkAction()
    data class OnCategoryChange(val category: DrinkCategory) : AddDrinkAction()
    data class OnDateChange(val date: Long?) : AddDrinkAction()
    data class OnTimeChange(val time: TimePickerState) : AddDrinkAction()
    data class OnSliderRatioChange(val sliderRatio: Float) : AddDrinkAction()
}