package com.example.poputka.presentation.home.home_screen.drink_log_panel

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun DrinkItemIcon(
    @ColorRes backgroundColor: Int,
    @DrawableRes iconId: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(iconId),
        contentDescription = null,
        modifier = modifier
            .size(28.dp)
            .background(color = colorResource(backgroundColor), shape = CircleShape),
        contentScale = ContentScale.Inside
    )
}