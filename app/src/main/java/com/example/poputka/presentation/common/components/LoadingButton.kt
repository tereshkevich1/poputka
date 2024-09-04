package com.example.poputka.presentation.common.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingButton(
    isLoading: Boolean,
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !isLoading,
        shape = MaterialTheme.shapes.medium
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically),
                strokeWidth = 2.dp
            )
        } else {
            Text(text = buttonText)
        }
    }
}
