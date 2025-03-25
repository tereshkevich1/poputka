package com.example.poputka.feature_journal.presentation.charts.calendar_chart.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.poputka.R

@Composable
fun NextButton(onClick: () -> Unit, iconSize: Dp = 32.dp) {
    IconButton(onClick = onClick, modifier = Modifier.fillMaxSize()) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
            contentDescription = "Arrow right icon",
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
fun PreviousButton(onClick: () -> Unit, iconSize: Dp = 32.dp) {
    IconButton(onClick = onClick, modifier = Modifier.fillMaxSize()) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_left_24),
            contentDescription = "Arrow left icon",
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
fun DateLabel(currentDate: String) {
    Text(text = currentDate)
}