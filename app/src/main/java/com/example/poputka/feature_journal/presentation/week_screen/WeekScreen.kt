package com.example.poputka.feature_journal.presentation.week_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.poputka.common.presentation.constants.UiConstants.bottomNavAndFabPadding
import com.example.poputka.common.presentation.models.asUiText
import com.example.poputka.feature_journal.presentation.charts.bar_chart.BarChart
import com.example.poputka.feature_journal.presentation.charts.bar_chart.Bars
import com.example.poputka.feature_journal.presentation.charts.bar_chart.animation.fadeInAnimation
import com.example.poputka.feature_journal.presentation.charts.bar_chart.xaxis.graph_modes.WeekMode
import com.example.poputka.feature_journal.presentation.charts.calendar_chart.DateNavigationBar
import com.example.poputka.feature_journal.presentation.journal_screen.components.DrinkRecordItem
import com.example.poputka.feature_journal.presentation.journal_screen.components.HeaderRecords
import com.example.poputka.ui.theme.DpSpSize.paddingSmall
import kotlin.random.Random


@Composable
fun WeekScreen(
    state: WeekState,
    onAction: (WeekScreenAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val volumeUnit = state.volumeUnit.asUiText().asString()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = bottomNavAndFabPadding)
    ) {
        item {
            DateNavigationBar(
                currentDatePeriod = state.formattedDateRange,
                onPrevious = { onAction(WeekScreenAction.OnPreviousPeriodClick) },
                onNext = { onAction(WeekScreenAction.OnNextPeriodClick) },
                totalHydration = state.totalHydration.formatted,
                volumeUnit = volumeUnit
            )


            val chartMode = WeekMode()

            val numberOfBars = chartMode.getBarCount()
            val max = 10.0f
            val min = 0f

            val barsListM = Bars(
                bars = state.bars, achievementValue = Random.nextFloat() * (max - min) + min
            )

            BarChart(
                bars = barsListM,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                animation = fadeInAnimation(1500),
                chartMode = chartMode
            )

            Spacer(modifier = Modifier.height(paddingSmall))
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
            HeaderRecords(onAddNewRecordClick = { onAction(WeekScreenAction.OnAddRecordClick) })
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        items(
            items = state.records
        ) { record ->

            DrinkRecordItem(
                time = record.date.formatted,
                volume = record.totalVolume.formatted,
                volumeUnit = volumeUnit,
            )

        }
    }
}