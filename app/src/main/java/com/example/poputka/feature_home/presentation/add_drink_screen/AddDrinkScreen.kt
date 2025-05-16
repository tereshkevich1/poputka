@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.poputka.feature_home.presentation.add_drink_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.poputka.R
import com.example.poputka.common.global_state.local_settings_state.LocalSettingsState
import com.example.poputka.feature_home.presentation.add_drink_screen.components.DrinkVolumeInputField
import com.example.poputka.feature_home.presentation.add_drink_screen.components.date_time_block.TimeRow
import com.example.poputka.feature_home.presentation.add_drink_screen.components.drink_category_selector.DrinkCategorySelector
import com.example.poputka.feature_home.presentation.add_drink_screen.components.drink_liquid_control.DrinkLiquidControl
import com.example.poputka.feature_home.presentation.add_drink_screen.components.favorite_drinks_block.FavoriteDrinksBlock
import com.example.poputka.ui.theme.DpSpSize.paddingExtraLarge
import com.example.poputka.ui.theme.DpSpSize.paddingMedium
import com.example.poputka.ui.theme.DpSpSize.paddingSmall
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun AddDrinkRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddDrinkViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launch {
            viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is AddDrinkEvent.ShowToast -> Toast.makeText(
                            context,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()

                        AddDrinkEvent.NavigateBack -> onBack()
                    }
                }
        }
    }

    AddDrinkScreen(
        state = uiState,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

@Composable
fun AddDrinkScreen(
    state: AddDrinkUiState,
    onAction: (AddDrinkAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val volumeUnit = LocalSettingsState.current.volumeUnitSetting

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_drink_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(AddDrinkAction.OnBackClick) }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            stringResource(R.string.back_icon)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalDivider(Modifier.fillMaxWidth())

            TimeRow(
                displayableTime = state.time,
                displayableDate = state.date,
                onDateSelect = { onAction(AddDrinkAction.OnDateChange(it)) },
                onDateDismiss = {},
                onTimeSelect = { onAction(AddDrinkAction.OnTimeChange(it)) },
                onTimeDismiss = {},
                modifier = Modifier.padding(horizontal = paddingMedium)
            )
            Spacer(Modifier.weight(1f))

            DrinkVolumeInputField(
                drinkVolume = state.volume,
                onDrinkVolumeChange = { onAction(AddDrinkAction.OnVolumeChange(it)) }
            )

            Spacer(Modifier.height(paddingSmall))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = paddingMedium)
            ) {
                DrinkLiquidControl(
                    color = colorResource(state.drinkCategory.colorId),
                    modifier = Modifier
                        .fillMaxHeight(0.6f)
                        .width(120.dp)
                        .align(Alignment.Center)
                )

                FavoriteDrinksBlock(modifier = Modifier.align(Alignment.CenterEnd))
            }


            DrinkCategorySelector(
                onDrinkCategoryChange = {
                    onAction(AddDrinkAction.OnCategoryChange(it))
                }
            )

            Button(
                onClick = { onAction(AddDrinkAction.OnAddClick(volumeUnit)) },
                enabled = state.volume.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = paddingExtraLarge)
            ) {
                Text(text = stringResource(R.string.add_drink))
            }

            Spacer(Modifier.weight(1f))
        }
    }
}



