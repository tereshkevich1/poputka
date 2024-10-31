package com.example.poputka.presentation.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Example() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val x = size.width / 2
        val y = size.width / 2
        drawCircle(Color.Green, radius = 22f, center = Offset(x,y), alpha = 0.2f)
        drawCircle(Color.Green, radius = 14f, center = Offset(x,y))
        drawCircle(Color.White, radius = 10f, center = Offset(x,y))
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


