@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.poputka.feature_home.presentation.add_drink_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.poputka.feature_home.domain.use_case.UpdateValueUseCase
import com.example.poputka.common.domain.use_case.format.DateFormatUseCase
import com.example.poputka.common.domain.use_case.format.TimeFormatUseCase
import com.example.poputka.feature_home.presentation.add_drink_screen.components.AddDrinkContent
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun AddDrinkScreen(
    viewModel: AddDrinkViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    AddDrinkContent(
        state = uiState.value,
        time = viewModel.getTime(uiState.value.time),
        date = viewModel.getDate(uiState.value.date),
        onDateSelect = { viewModel.setDate(it) },
        onTimeSelect = { viewModel.setTime(it) },
        onDrinkCategoryChange = { newDrinkCategory ->
            viewModel.changeDrinkCategory(
                newDrinkCategory
            )
        },
        drinkVolume = uiState.value.volume,
        onDrinkVolumeChange = { newValue ->
            viewModel.changeVolume(newValue)
        }
    )
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun AddDrinkScreenPreview() {
    PoputkaTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AddDrinkScreen(
                viewModel = AddDrinkViewModel(
                    dateFormatUseCase = DateFormatUseCase(), TimeFormatUseCase(), UpdateValueUseCase()
                )
            )
        }
    }
}


