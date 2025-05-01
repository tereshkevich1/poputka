package com.example.poputka.common.presentation.components.selectors.vertical_selector

import android.util.Log
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.poputka.common.presentation.components.selectors.common.ScrollStateManager
import com.example.poputka.common.presentation.components.selectors.util.Orientation
import com.example.poputka.common.presentation.util.dpToPx
import com.example.poputka.common.presentation.components.selectors.util.VerticalScrollUtils.calculateCenterIndex
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun VerticalSelector(
    values: List<String>,
    onIndexChanged: (Int) -> Unit = {},
    preselectedIndex: Int = 0,
    height: Dp = 200.dp,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    modifier: Modifier = Modifier
) {
    val textStyleHeight = textStyle.lineHeight.value
    val verticalPadding = (height - textStyleHeight.dp) / 2
    val verticalPaddingPx = dpToPx(verticalPadding)

    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = preselectedIndex
    )
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
    val coroutineScope = rememberCoroutineScope()

    var lastReportedIndex = remember { preselectedIndex }

    val scrollStateManager = remember {
        ScrollStateManager(
            listState = lazyListState,
            coroutineScope = coroutineScope,
            paddingPx = verticalPaddingPx,
            orientation = Orientation.VERTICAL
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            onIndexChanged(lastReportedIndex)
            Log.d("lazyListStateLog", "$lastReportedIndex - on dispose")
        }
    }

    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (lazyListState.isScrollInProgress) return@LaunchedEffect

        lastReportedIndex = calculateCenterIndex(lazyListState, verticalPaddingPx)
        onIndexChanged(lastReportedIndex)
        Log.d("lazyListStateLog", "$lastReportedIndex")
    }

    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(vertical = verticalPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        flingBehavior = snapBehavior,
        modifier = modifier
            .height(height)
            .defaultMinSize(minWidth = 52.dp)
    ) {
        itemsIndexed(values) { index, value ->
            VerticalSelectorItem(
                listState = lazyListState,
                index = index,
                value = value,
                verticalPaddingPx = verticalPaddingPx,
                textStyle = textStyle,
                onClick = {
                    scrollStateManager.scrollTo(index)
                }
            )
        }
    }
}

@Composable
@Preview
fun VerticalSelectorPreview() {
    val ages: MutableList<String> = mutableListOf()
    val range = 11..20
    range.forEach {
        ages.add(it.toString())
    }
    PoputkaTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    VerticalSelector(ages)
                    Text(",", style = MaterialTheme.typography.headlineMedium)
                    VerticalSelector(
                        listOf(
                            "0.0",
                            "0.1",
                            "0.2",
                            "0.3",
                            "0.4",
                            "0.5",
                            "0.6",
                            "0.7",
                            "0.8",
                            "0.9"
                        )
                    )
                    VerticalSelector(
                        listOf("kg", "lbs"),
                        textStyle = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}
