@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.poputka.presentation.add_drink_screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.ViewModel
import com.example.poputka.domain.use_case.format.DateFormatUseCase
import com.example.poputka.domain.use_case.format.TimeFormatUseCase
import com.example.poputka.presentation.util.DrinkCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddDrinkViewModel @Inject constructor(
    private val dateFormatUseCase: DateFormatUseCase,
    private val timeFormatUseCase: TimeFormatUseCase
) : ViewModel() {
    private var _uiState = MutableStateFlow(
        AddDrinkUiState()
    )
    val uiState = _uiState.asStateFlow()

    fun getDate(timestamp: Long) = dateFormatUseCase(timestamp)

    fun getTime(timestamp: Long) = timeFormatUseCase(timestamp)

    fun setDate(newDate: Long?) {
        _uiState.value = _uiState.value.copy(date = newDate ?: System.currentTimeMillis())
    }

    fun setTime(newTime: TimePickerState) {
        val hours = newTime.hour
        val minutes = newTime.minute
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val timeInMillis = calendar.timeInMillis

        _uiState.value = _uiState.value.copy(time = timeInMillis)
    }

    fun changeDrinkCategory(newDrinkCategory: DrinkCategory?) {
        newDrinkCategory?.let { drinkCategory ->
            _uiState.value = _uiState.value.copy(drinkCategory = drinkCategory)
        }
    }
}

data class AddDrinkUiState(
    var time: Long = System.currentTimeMillis(),
    var date: Long = System.currentTimeMillis(),
    var drinkCategory: DrinkCategory = DrinkCategory.Water,
    var quantity: Int = 0
)