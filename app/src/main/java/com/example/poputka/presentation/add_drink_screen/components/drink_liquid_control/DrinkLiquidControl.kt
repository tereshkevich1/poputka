package com.example.poputka.presentation.add_drink_screen.components.drink_liquid_control

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun DrinkLiquidControl(color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(100.dp))
                .background(color)
                .pointerInput(Unit) {
                    detectVerticalDragGestures { _, _ ->
                    }
                }
        ) {
        }
    }
}