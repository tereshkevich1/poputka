package com.example.poputka.presentation.home.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poputka.R
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize()) {

    }
}

@Composable
fun DrinkItem(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(
                Color.Gray,
                RoundedCornerShape(24.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        DrinkItemIcon()
        Spacer(modifier = Modifier.weight(1f))
        Text("Water")
        Spacer(modifier = Modifier.weight(1f))
        Text("200ml", modifier = Modifier.padding(end = 8.dp))
    }
}

@Composable
fun DrinkItemIcon() {
    Image(
        painter = painterResource(R.drawable.heart_check),
        contentDescription = null,
        modifier = Modifier
            .size(28.dp)
            .background(Color.Cyan, CircleShape)

    )
}

@Composable
fun DrinkLogPanel() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.horizontal_default_padding))
            .background(color = Color.DarkGray, shape = RoundedCornerShape(24.dp))
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DrinkItem(
                modifier = Modifier.padding(
                    start = 8.dp,
                    top = 8.dp,
                    end = 4.dp,
                    bottom = 4.dp
                )
            )
            DrinkItem(
                modifier = Modifier.padding(
                    start = 8.dp,
                    top = 4.dp,
                    end = 4.dp,
                    bottom = 4.dp
                )
            )
            DrinkItem(
                modifier = Modifier.padding(
                    start = 8.dp,
                    top = 4.dp,
                    end = 4.dp,
                    bottom = 8.dp
                )
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DrinkItem(
                modifier = Modifier.padding(
                    start = 4.dp,
                    top = 8.dp,
                    end = 8.dp,
                    bottom = 4.dp
                )
            )
            DrinkItem(
                modifier = Modifier.padding(
                    start = 4.dp,
                    top = 4.dp,
                    end = 8.dp,
                    bottom = 4.dp
                )
            )
            DrinkItem(
                modifier = Modifier.padding(
                    start = 4.dp,
                    top = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
            )
        }

    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    PoputkaTheme {
        HomeScreen()
    }
}


@Composable
@Preview
fun DrinkItemPreview() {
    PoputkaTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(contentAlignment = Alignment.Center) {
                DrinkLogPanel()
            }
        }
    }
}