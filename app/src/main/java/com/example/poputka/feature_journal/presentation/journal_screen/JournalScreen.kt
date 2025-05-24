package com.example.poputka.feature_journal.presentation.journal_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.poputka.R
import com.example.poputka.common.domain.AppStateHolder
import com.example.poputka.common.domain.model.VolumeUnit
import com.example.poputka.common.presentation.models.DisplayableInt
import com.example.poputka.common.presentation.models.DisplayableLong
import com.example.poputka.common.presentation.models.asUiText
import com.example.poputka.common.presentation.models.mappers.toDisplayableDate
import com.example.poputka.common.presentation.models.mappers.toDisplayableVolume
import com.example.poputka.core.presentation.BaseViewModel
import com.example.poputka.feature_journal.presentation.charts.calendar_chart.DateNavigationBar
import com.example.poputka.feature_journal.presentation.day_screen.DayScreen
import com.example.poputka.feature_journal.presentation.day_screen.DayViewModel
import com.example.poputka.feature_journal.presentation.week_screen.WeekScreen
import com.example.poputka.feature_journal.presentation.week_screen.WeekViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun JournalScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: JournalViewModel = hiltViewModel()
) {
    val state by viewModel.settingsState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launch {
            viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is JournalScreenEvent.ShowToast -> TODO()
                    }
                }
        }
    }

    JournalScreen(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

@Composable
fun JournalScreen(
    state: JournalUiState,
    onAction: (JournalScreenAction) -> Unit,
    modifier: Modifier
) {

    val volumeUnit = state.unit.asUiText().asString()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TabRow(selectedTabIndex = state.selectedIndex) {
                state.tabs.forEachIndexed { index, tab ->
                    Tab(
                        text = { Text(stringResource(tab.titleResId)) },
                        selected = state.selectedIndex == index,
                        onClick = { onAction(JournalScreenAction.OnTabSelected(tab)) }
                    )
                }
            }

            when (state.selectedTab) {
                JournalTab.Day -> {

                    val dayViewModel: DayViewModel = hiltViewModel()
                    val dayState by dayViewModel.dayState.collectAsStateWithLifecycle()
                    DayScreen(state = dayState, onAction = dayViewModel::onAction)
                }

                JournalTab.Week -> {
                    val weekViewModel: WeekViewModel = hiltViewModel()
                    val weekState by weekViewModel.weekState.collectAsStateWithLifecycle()
                    WeekScreen(state = weekState, onAction = weekViewModel::onAction)
                }

                JournalTab.Month -> {
                    DateNavigationBar(
                        currentDatePeriod = state.dateRange.formatted,
                        onPrevious = { onAction(JournalScreenAction.OnPreviousPeriodClick) },
                        onNext = { onAction(JournalScreenAction.OnNextPeriodClick) },
                        totalHydration = state.totalHydration.formatted,
                        volumeUnit = volumeUnit
                    )
                    Text(text = "Month")
                }
            }
        }
    }
}

@HiltViewModel
class JournalViewModel @Inject constructor(appStateHolder: AppStateHolder) :
    BaseViewModel<JournalScreenEvent>() {
    private val _journalState = MutableStateFlow(JournalUiState())
    val settingsState = _journalState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        JournalUiState()
    )

    private val appPreferencesStateHolder = appStateHolder.appPreferencesStateHolder

    fun onAction(action: JournalScreenAction) {
        when (action) {
            JournalScreenAction.OnAddRecordClick -> TODO()
            JournalScreenAction.OnNextPeriodClick -> TODO()
            JournalScreenAction.OnPreviousPeriodClick -> TODO()
            is JournalScreenAction.OnRecordClick -> TODO()
            is JournalScreenAction.OnTabSelected -> changeTab(action.tab)
        }
    }

    private fun changeTab(tab: JournalTab) {
        _journalState.update {
            it.copy(
                selectedIndex = it.tabs.indexOf(tab),
                selectedTab = tab
            )
        }
    }
}

data class JournalUiState(
    val unit: VolumeUnit = VolumeUnit.Milliliters,
    val tabs: List<JournalTab> = listOf(JournalTab.Day, JournalTab.Week, JournalTab.Month),
    val selectedTab: JournalTab = JournalTab.Day,
    val selectedIndex: Int = tabs.indexOf(selectedTab),
    val dateRange: DisplayableLong = System.currentTimeMillis().toDisplayableDate(),
    val totalHydration: DisplayableInt = 0.toDisplayableVolume(unit),
    val records: List<DrinkRecordUi> = emptyList()
)


data class DrinkRecordUi(
    val id: Int,
    val time: DisplayableLong,
    val totalHydration: DisplayableInt
)


sealed class JournalScreenAction {
    data class OnTabSelected(val tab: JournalTab) : JournalScreenAction()
    data object OnPreviousPeriodClick : JournalScreenAction()
    data object OnNextPeriodClick : JournalScreenAction()
    data object OnAddRecordClick : JournalScreenAction()
    data class OnRecordClick(val recordId: Long) : JournalScreenAction()
}


enum class JournalTab(val titleResId: Int) {
    Day(R.string.day),
    Week(R.string.week),
    Month(R.string.month)
}


