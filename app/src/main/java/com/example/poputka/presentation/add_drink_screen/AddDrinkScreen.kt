@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.poputka.presentation.add_drink_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.poputka.R
import com.example.poputka.domain.use_case.format.DateFormatUseCase
import com.example.poputka.domain.use_case.format.TimeFormatUseCase
import com.example.poputka.presentation.add_drink_screen.components.DrinkCategoryCard
import com.example.poputka.presentation.add_drink_screen.components.TimeRow
import com.example.poputka.presentation.canvas.liquid_control.LiquidControl
import com.example.poputka.presentation.util.DrinkCategory
import com.example.poputka.presentation.util.dpToInt
import com.example.poputka.presentation.util.drinkCategories
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun AddDrinkScreen(
    viewModel: AddDrinkViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    val innerPadding = dimensionResource(id = R.dimen.padding_medium)
    val smallPadding = dimensionResource(id = R.dimen.padding_small)
    val outlineVariantColor = MaterialTheme.colorScheme.outlineVariant

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DividerWithSpace(outlineVariantColor, 20.dp)
        TimeRow(
            uiState.value.time,
            uiState.value.date,
            viewModel.getTime(uiState.value.time),
            viewModel.getDate(uiState.value.date),
            onDateSelected = { viewModel.setDate(it) },
            onDateDismiss = {},
            onTimeSelected = { viewModel.setTime(it) },
            onTimeDismiss = {},
            modifier = Modifier.padding(horizontal = innerPadding, vertical = smallPadding)
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = outlineVariantColor
        )
        LiquidControl(colorResource(uiState.value.drinkCategory.colorId))

        DrinkCategorySelector(onDrinkCategoryChange = { newDrinkCategory ->
            viewModel.changeDrinkCategory(
                newDrinkCategory
            )
        })
    }
}

@Composable
fun DividerWithSpace(color: Color, height: Dp) {
    Spacer(modifier = Modifier.height(height))
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = color
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
                    dateFormatUseCase = DateFormatUseCase(), TimeFormatUseCase()
                )
            )
        }
    }
}


@Composable
fun DrinkCategorySelector(onDrinkCategoryChange: (newDrinkCategory: DrinkCategory?) -> Unit) {

    val lazyListState = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

    val cardWidth = 64.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val horizontalLazyRowPadding = screenWidth / 2 - cardWidth / 2
    val horizontalLazyRowPaddingInt = dpToInt(horizontalLazyRowPadding)

    val currentIndex by remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.firstOrNull { item ->
                val delta = item.size / 2
                val center =
                    (horizontalLazyRowPaddingInt + lazyListState.layoutInfo.viewportEndOffset) / 2
                val childCenter = horizontalLazyRowPaddingInt + item.offset + item.size / 2
                val target = childCenter - center
                target in -delta..delta
            }?.index ?: -1
        }
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override suspend fun onPostFling(
                consumed: androidx.compose.ui.unit.Velocity,
                available: androidx.compose.ui.unit.Velocity
            ): androidx.compose.ui.unit.Velocity {
                Log.d("nesty", currentIndex.toString())
                onDrinkCategoryChange(drinkCategories.getOrNull(currentIndex))
                return super.onPostFling(consumed, available)
            }
        }
    }

    LazyRow(
        modifier = Modifier
            .wrapContentHeight(Alignment.CenterVertically)
            .fillMaxWidth()
            .nestedScroll(nestedScrollConnection),
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = horizontalLazyRowPadding),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top,
        flingBehavior = snapBehavior
    ) {
        itemsIndexed(drinkCategories) { index, drinkCategory ->
            DrinkCategoryCard(
                lazyListState,
                index,
                horizontalLazyRowPaddingInt,
                colorResource(drinkCategory.colorId),
                painterResource(drinkCategory.iconId),
                stringResource(drinkCategory.nameId),
                cardWidth
            )
        }
    }
}

@Composable
@Preview
fun HorizontalPagerPreview() {
    PoputkaTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                DrinkCategorySelector({})
            }
        }
    }
}

