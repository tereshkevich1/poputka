package com.example.poputka.presentation.add_drink_screen.components.drink_category_selector

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.example.poputka.presentation.util.DrinkCategory
import com.example.poputka.presentation.util.dpToInt
import com.example.poputka.presentation.util.drinkCategories

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
                consumed: Velocity,
                available: Velocity
            ): Velocity {
                onDrinkCategoryChange(drinkCategories.getOrNull(currentIndex))
                return super.onPostFling(consumed, available)
            }
        }
    }

    LazyRow(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .nestedScroll(nestedScrollConnection),
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = horizontalLazyRowPadding),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
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