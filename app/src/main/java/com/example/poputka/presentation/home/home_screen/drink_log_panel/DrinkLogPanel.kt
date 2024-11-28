package com.example.poputka.presentation.home.home_screen.drink_log_panel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poputka.R
import com.example.poputka.presentation.home.util.DrinkCategory
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun DrinkLogPanel(recentDrinks: List<Pair<DrinkCategory, String>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.horizontal_default_padding))
            .clip(shape = RoundedCornerShape(24.dp))
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .clickable { }
    ) {

        DrinkLogHeader()

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer),
            contentPadding = PaddingValues(4.dp),
            userScrollEnabled = false
        ) {
            items(recentDrinks) { item ->
                DrinkListItem(
                    item.first,
                    item.second,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Composable
@Preview
fun DrinkItemPreview() {
    val list = listOf(
        Pair(DrinkCategory.EnergyDrink, "200"),
        Pair(DrinkCategory.Wine, "200"),
        Pair(DrinkCategory.Water, "200"),
        Pair(DrinkCategory.Tea, "200"),
        Pair(DrinkCategory.Coffee, "200"),
        Pair(DrinkCategory.Beer, "200")
    )
    PoputkaTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(contentAlignment = Alignment.Center) {
                DrinkLogPanel(list)
            }
        }
    }
}