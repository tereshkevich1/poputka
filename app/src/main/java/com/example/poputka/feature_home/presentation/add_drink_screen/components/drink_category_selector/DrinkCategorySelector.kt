package com.example.poputka.feature_home.presentation.add_drink_screen.components.drink_category_selector

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.poputka.common.presentation.DrinkCategory
import com.example.poputka.common.presentation.components.selectors.common.ScrollStateManager
import com.example.poputka.common.presentation.components.selectors.util.HorizontalScrollUtils.calculateCenterIndex
import com.example.poputka.common.presentation.components.selectors.util.Orientation
import com.example.poputka.common.presentation.drinkCategories
import com.example.poputka.common.presentation.util.dpToPx

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun DrinkCategorySelector(onDrinkCategoryChange: (newDrinkCategory: DrinkCategory) -> Unit) {
    val listState = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(listState)

    val cardWidth = 64.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val horizontalLazyRowPadding = screenWidth / 2 - cardWidth / 2
    val horizontalLazyRowPaddingPx = dpToPx(horizontalLazyRowPadding)

    val coroutineScope = rememberCoroutineScope()
    val scrollStateManager = remember {
        ScrollStateManager(
            listState = listState,
            coroutineScope = coroutineScope,
            paddingPx = horizontalLazyRowPaddingPx,
            orientation = Orientation.HORIZONTAL
        )
    }

    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) return@LaunchedEffect
        val currentIndex = calculateCenterIndex(listState, horizontalLazyRowPaddingPx)
        drinkCategories.getOrNull(currentIndex)?.let {
            onDrinkCategoryChange(it)
        }
    }

    LazyRow(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth(),
        state = listState,
        contentPadding = PaddingValues(horizontal = horizontalLazyRowPadding),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        flingBehavior = snapBehavior
    ) {
        itemsIndexed(drinkCategories) { index, drinkCategory ->
            DrinkCategoryCard(
                onClick = { scrollStateManager.scrollTo(index) },
                listState = listState,
                index = index,
                horizontalLazyRowPadding = horizontalLazyRowPaddingPx,
                backgroundColor = colorResource(drinkCategory.colorId),
                icon = painterResource(drinkCategory.iconId),
                drinkName = stringResource(drinkCategory.nameId),
                cardWidth = cardWidth
            )
        }
    }
}