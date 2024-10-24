package com.example.poputka.presentation.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Example() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        
    }
}

@Composable
@Preview
fun CanvasPreview2() {
    MaterialTheme {
        Surface(
            modifier = Modifier
                .size(400.dp)
        ) {
            Example()
        }
    }
}


