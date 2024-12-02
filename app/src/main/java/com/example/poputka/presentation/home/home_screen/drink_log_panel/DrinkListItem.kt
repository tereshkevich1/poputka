package com.example.poputka.presentation.home.home_screen.drink_log_panel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poputka.R
import com.example.poputka.presentation.home.util.DrinkCategory
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun DrinkListItem(drink: DrinkCategory, amount: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        DrinkItemIcon(drink.colorId, drink.iconId)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(drink.nameId),
            modifier = Modifier.weight(5.5f),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            amount,
            modifier = Modifier
                .padding(end = 12.dp)
                .weight(4f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun DrinkListItemFull(
    drink: DrinkCategory,
    amount: String,
    time: String,
    onAddButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .height(64.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surfaceContainer),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DrinkItemIcon(
            backgroundColor = drink.colorId,
            iconId = drink.iconId,
            modifier = Modifier.padding(start = 16.dp)
        )
        Column(
            modifier = Modifier.padding(start = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(drink.nameId),
                modifier = Modifier,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = time,
                modifier = Modifier,
                color = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.weight(0.5f))

        IconButton(onClick = onAddButtonClick, modifier = Modifier) {
            Icon(
                painter = painterResource(R.drawable.add),
                contentDescription = null
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .padding(end = 20.dp)
                .weight(0.9f)
        ) {
            Text(
                amount,
                modifier = Modifier,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "${stringResource(R.string.hydration_title)}: ${drink.hydration}%",
                modifier = Modifier,
                color = MaterialTheme.colorScheme.outline,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )
        }

    }
}

@Composable
@Preview
fun DrinkListItemPreview() {
    PoputkaTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                DrinkListItemFull(
                    DrinkCategory.Water,
                    "900 ml",
                    "03:00",
                    {},
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}