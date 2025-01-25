@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka.presentation.add_drink_screen.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.poputka.presentation.util.clearFocusOnKeyboardDismiss

@Composable
fun DrinkVolumeInputField(drinkVolume: String, onDrinkVolumeChange: (String) -> Unit) {

    val interactionSource = remember { MutableInteractionSource() }
    val enabled = true
    val singleLine = true

    BasicTextField(
        value = drinkVolume,
        onValueChange = { onDrinkVolumeChange(it) },
        interactionSource = interactionSource,
        enabled = enabled,
        textStyle = MaterialTheme.typography.headlineMedium.copy(MaterialTheme.colorScheme.onSurface),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = singleLine,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .wrapContentHeight()
            .clearFocusOnKeyboardDismiss(),
    ) {
        TextFieldDefaults.DecorationBox(
            value = drinkVolume,
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            singleLine = singleLine,
            enabled = enabled,
            interactionSource = interactionSource,
            suffix = { Text("ml") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                top = 0.dp,
                bottom = 0.dp,
                start = 8.dp,
                end = 8.dp,
            )
        )
    }
}