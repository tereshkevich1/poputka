package com.example.poputka.feature_home.presentation.home_screen.drink_log_panel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.poputka.R

@Composable
fun DrinkAmount(
    amount: String,
    hydration: Int,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        Text(
            amount,
            modifier = Modifier,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "${stringResource(R.string.hydration_title)}: $hydration%",
            modifier = Modifier,
            color = MaterialTheme.colorScheme.outline,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall
        )
    }
}