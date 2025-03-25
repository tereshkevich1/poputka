package com.example.poputka.core.presentation.navigation.custom_nav_bar

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class CustomCutoutShape(
    private val cutoutWidth: Dp = 52.dp,
    private val cutoutHeight: Dp = 36.dp,
    private val cornerRadius: Dp = 36.dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val cutoutWidthPx = with(density) { cutoutWidth.toPx() }
        val cutoutHeightPx = with(density) { cutoutHeight.toPx() }
        val cornerRadiusPx = with(density) { cornerRadius.toPx() }

        val path = Path().apply {
            val left = size.width / 2 - cutoutWidthPx / 2
            val right = size.width / 2 + cutoutWidthPx / 2

            moveTo(0f, 0f)
            lineTo(left - cornerRadiusPx, 0f)

            cubicTo(
                left, 0f,
                left - cutoutWidthPx / 4, cutoutHeightPx,
                size.width / 2, cutoutHeightPx
            )
            cubicTo(
                right + cutoutWidthPx / 4, cutoutHeightPx,
                right, 0f,
                right + cornerRadiusPx, 0f
            )

            lineTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}