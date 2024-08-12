package com.example.poputka.presentation.auth_screen.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class NanpVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 9) text.text.substring(0..8) else text.text

        var out = if (trimmed.isNotEmpty()) "(" else ""

        for (i in trimmed.indices) {
            if (i == 2) out += ") "
            if (i == 5) out += "-"
            if (i == 7) out += "-"

            out += trimmed[i]
        }
        return TransformedText(AnnotatedString(out), phoneNumberOffsetTranslator)
    }

    private val phoneNumberOffsetTranslator = object : OffsetMapping {

        override fun originalToTransformed(offset: Int): Int =
            when (offset) {
                0 -> offset
                // Add 1 for opening parenthesis.
                in 1..2 -> offset + 1
                // Add 3 for both parentheses and a space.
                in 3..5 -> offset + 3
                // Add 3 for both parentheses, space, and hyphen.
                in 6..7 -> offset + 4
                // Add 3 for both parentheses, space, and 2 hyphen.
                else -> offset + 5
            }

        override fun transformedToOriginal(offset: Int): Int =
            when (offset) {
                0 -> offset
                // Subtract 1 for opening parenthesis.
                in 1..4 -> offset - 1
                // Subtract 3 for both parentheses and a space.
                in 5..7 -> offset - 3
                // Subtract 4 for both parentheses, space, and hyphen.
                in 8..10 -> offset - 4
                // Subtract 5 for both parentheses, space, and 2 hyphen.
                else -> offset - 5
            }

    }
}