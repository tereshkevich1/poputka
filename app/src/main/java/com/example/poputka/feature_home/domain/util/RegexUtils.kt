package com.example.poputka.feature_home.domain.util

object RegexUtils {
    val nonZeroNumberRegex = Regex("[1-9][0-9]*")
    val numberRegex = Regex("[0-9]+")
    val leadingZerosRegex = Regex("0+")
}