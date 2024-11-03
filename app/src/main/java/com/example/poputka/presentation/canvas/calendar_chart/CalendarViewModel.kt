package com.example.poputka.presentation.canvas.calendar_chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class CalendarViewModel: ViewModel() {
    private var _currentDate = MutableStateFlow(YearMonth.now())
    val currentDate: StateFlow<YearMonth> = _currentDate

    fun previousMonth() {
        viewModelScope.launch {
            _currentDate.value = _currentDate.value.minusMonths(1)
        }
    }

    fun nextMonth() {
        viewModelScope.launch {
            _currentDate.value = _currentDate.value.plusMonths(1)
        }
    }

    fun getCurrentMonthAsString(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy MMMM", Locale.getDefault())
        return _currentDate.value.format(formatter)
    }
}