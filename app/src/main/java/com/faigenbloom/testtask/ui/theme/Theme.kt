package com.faigenbloom.testtask.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    background = background,
    onBackground = onBackground,
    onPrimary = onPrimary,
    primary = primary,
    secondary = secondary,
    tertiary = tertiary,
    primaryContainer = primaryContainer,
    secondaryContainer = secondaryContainer,
    tertiaryContainer = tertiaryContainer,
    onPrimaryContainer = onPrimaryContainer,
    onSecondaryContainer = onSecondaryContainer,
    onTertiaryContainer = onTertiaryContainer,
    error = error,
    onError = onError,
)
private val LightColorScheme = lightColorScheme(
    background = background,
    onBackground = onBackground,
    onPrimary = onPrimary,
    primary = primary,
    secondary = secondary,
    tertiary = tertiary,
    primaryContainer = primaryContainer,
    secondaryContainer = secondaryContainer,
    tertiaryContainer = tertiaryContainer,
    onPrimaryContainer = onPrimaryContainer,
    onSecondaryContainer = onSecondaryContainer,
    onTertiaryContainer = onTertiaryContainer,
    error = error,
    onError = onError,
)

@Composable
fun TestTaskTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}

fun ColorScheme.tint() = tint
fun ColorScheme.transparent() = transparent
fun ColorScheme.fabGradientList() = fabGradientList
