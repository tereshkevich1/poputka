package com.example.poputka.presentation.home.home_screen.hydration_info_panel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HydrationInfoBlock(title: String, value: String, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val startPadding = 32.dp

        Text(
            text = value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = startPadding),
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = startPadding),
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.titleSmall
        )
    }
}