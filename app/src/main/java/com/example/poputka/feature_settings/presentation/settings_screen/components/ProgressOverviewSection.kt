package com.example.poputka.feature_settings.presentation.settings_screen.components

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
import com.example.poputka.ui.theme.DpSpSize.paddingMedium

@Composable
fun ProgressOverviewSection(totalConsumed: String, daysCompleted: String) {
    val iconColor = MaterialTheme.colorScheme.secondary
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        MetricCard(
            icon = painterResource(R.drawable.water_drop),
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