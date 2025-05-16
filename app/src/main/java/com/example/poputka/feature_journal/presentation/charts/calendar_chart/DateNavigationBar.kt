package com.example.poputka.feature_journal.presentation.charts.calendar_chart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.poputka.R
import com.example.poputka.ui.theme.DpSpSize.paddingMedium

@Composable
fun DateNavigationBar(
    modifier: Modifier = Modifier,
    currentDatePeriod: String,
    volumeUnit: String,
    totalHydration: String,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = paddingMedium)
    ) {
        IconButton(onClick = onPrevious, modifier = Modifier) {
            Icon(
                painter = painterResource(id = R.drawable.keyboard_arrow_left),
                contentDescription = "Arrow left icon",
                modifier = Modifier.size(32.dp)
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = currentDatePeriod,
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                Icon(
                    painter = painterResource(R.drawable.water_full),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$totalHydration $volumeUnit",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        IconButton(onClick = onNext, modifier = Modifier) {
            Icon(
                painter = painterResource(id = R.drawable.keyboard_arrow_right),
                contentDescription = "Arrow right icon",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}