package com.example.poputka.feature_home.presentation.home_screen.drink_log_panel

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.poputka.R

@Composable
fun AddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick, modifier = modifier.padding(end = 8.dp)) {
        Icon(
            painter = painterResource(R.drawable.add),
            contentDescription = null
        )
    }
}