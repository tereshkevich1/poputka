package com.example.poputka.presentation.home.home_screen.hydration_info_panel

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poputka.R
import com.example.poputka.presentation.home.home_screen.add_water_button.AddWaterButton
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun HydrationInfoPanel(
    currentHydration: String,
    allDrinksAmount: String,
    onIconButtonClick: () -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(88.dp)
            .padding(horizontal = dimensionResource(R.dimen.horizontal_default_padding))
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surfaceContainer),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HydrationInfoBlock(
            title = stringResource(R.string.hydration_info_block_title_hydration),
            value = currentHydration,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )

        VerticalDivider(modifier = Modifier.fillMaxHeight(), thickness = 1.dp)

        HydrationInfoBlock(
            title = stringResource(R.string.hydration_info_block_title_all_drinks),
            value = allDrinksAmount,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )

        IconButton(onClick = onIconButtonClick, modifier = Modifier) {
            Icon(
                painter = painterResource(R.drawable.help),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.weight(0.1f))
    }
}

@Composable
@Preview
fun HydroInfoPreview() {
    PoputkaTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HydrationInfoPanel("200", "3000", {}, Modifier)

                Spacer(Modifier.height(16.dp))

                AddWaterButton({},{}, modifier = Modifier)
            }
        }
    }
}