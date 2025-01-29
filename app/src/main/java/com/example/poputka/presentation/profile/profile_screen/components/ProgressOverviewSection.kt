package com.example.poputka.presentation.profile.profile_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.poputka.R

@Composable
fun ProgressOverviewSection(totalConsumed: String, daysCompleted: String) {
    val paddingMedium = dimensionResource(R.dimen.padding_medium)
    val iconColor = MaterialTheme.colorScheme.primary
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        MetricCard(
            icon = painterResource(R.drawable.baseline_water_drop),
            iconColor = iconColor,
            value = totalConsumed,
            label = stringResource(R.string.total_drunk),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(paddingMedium))
        MetricCard(
            icon = painterResource(R.drawable.day_streak),
            iconColor = iconColor,
            value = daysCompleted,
            label = stringResource(R.string.days_completed),
            modifier = Modifier.weight(1f)
        )
    }
}