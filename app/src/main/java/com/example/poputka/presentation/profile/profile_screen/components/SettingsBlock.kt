package com.example.poputka.presentation.profile.profile_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import com.example.poputka.R

@Composable
fun SettingsBlock(content: @Composable (ColumnScope.() -> Unit)) {
    val blockColor = MaterialTheme.colorScheme.surfaceContainerHigh
    val blockShape = MaterialTheme.shapes.large
    val paddingMedium = dimensionResource(R.dimen.padding_medium)
    Column(
        modifier = Modifier
            .padding(top = paddingMedium)
            .fillMaxWidth()
            .clip(blockShape)
            .background(blockColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) { content() }
}