package com.example.poputka.feature_home.presentation.home_screen.hydration_info_panel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.example.poputka.ui.theme.DpSpSize.paddingMedium
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
            .padding(horizontal = paddingMedium)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surfaceContainerLow),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HydrationInfoBlock(
            title = stringResource(R.string.hydration_info_block_title_hydration),
            value = currentHydration,
            modifier = Modifier
                .fillMaxHeight()
        )

        VerticalDivider(modifier = Modifier.height(52.dp), thickness = 1.dp)

        HydrationInfoBlock(
            title = stringResource(R.string.hydration_info_block_title_all_drinks),
            value = allDrinksAmount,
            modifier = Modifier
                .fillMaxHeight()
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onIconButtonClick, modifier = Modifier.padding(end = 8.dp)) {
            Icon(
                painter = painterResource(R.drawable.help),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
fun HydroInfoPreview() {
    PoputkaTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                HydrationInfoPanel("200", "3000", {}, Modifier.weight(5f))
            }
        }
    }
}