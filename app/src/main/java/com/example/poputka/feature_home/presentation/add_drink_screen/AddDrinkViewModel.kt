@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.poputka.feature_home.presentation.add_drink_screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.viewModelScope
import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.common.presentation.DrinkCategory
import com.example.poputka.common.presentation.models.mappers.toDisplayableTime
import com.example.poputka.common.presentation.models.mappers.toSmartDisplayableDate
import com.example.poputka.core.presentation.BaseViewModel
import com.example.poputka.feature_home.domain.models.Consumption
import com.example.poputka.feature_home.domain.repository.ConsumptionRepository
import com.example.poputka.feature_home.domain.use_case.UpdateValueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class AddDrinkViewModel @Inject constructor(
    private val updateValueUseCase: UpdateValueUseCase,
    private val consumptionRepository: ConsumptionRepository
) : BaseViewModel<AddDrinkEvent>() {
    private val _uiState = MutableStateFlow(AddDrinkUiState())
    val uiState = _uiState.asStateFlow()


    fun onAction(action: AddDrinkAction) {
        when (action) {
            AddDrinkAction.OnBackClick -> sendEvent(AddDrinkEvent.NavigateBack)
            is AddDrinkAction.OnAddClick -> {
                saveConsumption(action.volumeUnit)
                sendEvent(AddDrinkEvent.NavigateBack)
            }

            is AddDrinkAction.OnVolumeChange -> changeVolume(action.newVolume)
            is AddDrinkAction.OnCategoryChange -> changeDrinkCategory(action.category)
            is AddDrinkAction.OnDateChange -> setDate(action.date)
            is AddDrinkAction.OnTimeChange -> setTime(action.time)
            is AddDrinkAction.OnSliderRatioChange -> updateVolumeFromSliderRatio(action.sliderRatio)
        }
    }

    private fun saveConsumption(volumeUnit: VolumeUnit) {
        val volumeDouble = _uiState.value.volume.toDoubleOrNull()

        volumeDouble?.let {
            val drinkCategory = _uiState.value.drinkCategory
            val volumeMl = volumeUnit.convertToMilliliters(it)
            val hydrationVolume = volumeMl * drinkCategory.hydration / 100
            viewModelScope.launch {
                consumptionRepository.upsert(
                    Consumption(
                        drinkType = drinkCategory,
                        volume = volumeMl,
                        hydrationVolume = hydrationVolume,
                        timestamp = _uiState.value.time.value
                    )
                )
            }
            sendEvent(AddDrinkEvent.ShowToast("Напиток добавлен"))
        } ?: sendEvent(AddDrinkEvent.ShowToast("ошибка"))
    }


    private fun setDate(newDate: Long?) {
        newDate?.let {
            _uiState.update { it.copy(date = newDate.toSmartDisplayableDate()) }
        }
    }

    private fun setTime(newTime: TimePickerState) {
        val hours = newTime.hour
        val minutes = newTime.minute
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        _uiState.update {
            it.copy(time = calendar.timeInMillis.toDisplayableTime())
        }
    }

    private fun changeDrinkCategory(newDrinkCategory: DrinkCategory?) {
        newDrinkCategory?.let { drinkCategory ->
            _uiState.update {
                it.copy(drinkCategory = drinkCategory)
            }
        }
    }


    private fun changeVolume(newVolume: String) {
        updateValueUseCase(newVolume)?.let { validVolume ->
            _uiState.update {
                it.copy(
                    volume = validVolume,
                    currentVolumeFloat = validVolume.toFloatOrNull() ?: 0f,
                    volumeSliderRatio = validVolume.toFloatOrNull()?.div(it.maxVolumeFloat) ?: 0f
                )
            }
        }
    }

    private fun updateVolumeFromSliderRatio(sliderRatio: Float) {
        _uiState.update { state ->
            val step = 10f
            val maxVolume = state.maxVolumeFloat
            val desiredVolume = maxVolume * sliderRatio

            val clampedVolume = (desiredVolume / step).roundToInt() * step
                .coerceIn(0f, maxVolume)

            state.copy(
                volume = clampedVolume.toInt().toString(),
                currentVolumeFloat = clampedVolume,
                volumeSliderRatio = sliderRatio
            )
        }
    }
}








