package com.faigenbloom.testtask.ui.common

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun containerColor(isError: Boolean, isChecked: Boolean): Color {
    return if (isError.not()) {
        if (isChecked) {
            colorScheme.secondaryContainer
        } else {
            colorScheme.primaryContainer
        }
    } else {
        colorScheme.error
    }
}