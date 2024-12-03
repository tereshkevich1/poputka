package com.example.poputka.presentation.home.home_screen.day_streak_panel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.poputka.R

@Composable
fun DayStreakPanel(value: String, onPanelClick: () -> Unit, modifier: Modifier) {
    Column(
        modifier = modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .height(64.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surfaceContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(horizontal = 8.dp)) {
            Icon(painter = painterResource(R.drawable.day_streak), contentDescription = null)
            Text(text = "Day streak", style = MaterialTheme.typography.titleMedium)
        }

        Text("654", style = MaterialTheme.typography.titleLarge)
    }
}