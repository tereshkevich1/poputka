package com.example.poputka.feature_home.presentation.home_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.poputka.common.presentation.constants.UiConstants.bottomNavAndFabPadding
import com.example.poputka.common.presentation.models.asUiText
import com.example.poputka.feature_home.presentation.home_screen.custom_circular_progress_indicator.AnimatedCircularProgressIndicator
import com.example.poputka.feature_home.presentation.home_screen.drink_log_panel.DrinkItem
import com.example.poputka.feature_home.presentation.home_screen.drink_log_panel.DrinkLogHeader
import com.example.poputka.feature_home.presentation.home_screen.hydration_info_panel.HydrationInfoPanel
import com.example.poputka.ui.theme.DpSpSize.paddingMedium
import kotlinx.coroutines.launch

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launch {
            viewModel.events
                .flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { event ->
                    when (event) {
                        else -> {}
                    }
                }
        }
    }

    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()
    val unit = state.unit.asUiText().asString()

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = scrollState,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(
            bottom = bottomNavAndFabPadding
        )
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
            AnimatedCircularProgressIndicator(
                unit = state.unit,
                maxValue = state.goal,
                percent = state.progressPercent,
                currentValueProvider = { state.currentProgress }
            )

            Spacer(modifier = Modifier.height(32.dp))

            HydrationInfoPanel(
                currentHydration = "${state.totalHydro.formatted} $unit",
                allDrinksAmount = "${state.totalToday.formatted} $unit",
                onIconButtonClick = {}
            )

            DrinkLogHeader()
        }

        items(
            items = state.log,
            key = { it.id }
        ) { drink ->
            DrinkItem(
                drink = drink.drinkType,
                amount = "${drink.volume.formatted} $unit",
                time = drink.timestamp.formatted,
                onAddButtonClick = { onAction(HomeAction.OnDrinkClick(drink)) },
                modifier = Modifier.padding(horizontal = paddingMedium)
            )
        }
    }
}


