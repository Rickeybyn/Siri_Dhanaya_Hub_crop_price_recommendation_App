package com.siri.dhanyahub.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Green40,
    secondary = Brown40,
    tertiary = Gold40,
    background = Color(0xFFF7F3EA),
    surface = Color(0xFFFFFBF5),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = Dark40,
    onSurface = Dark40,
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF89B39A),
    secondary = Color(0xFFD1B08A),
    tertiary = Color(0xFFE2C45A),
    background = Color(0xFF121712),
    surface = Color(0xFF1A2119),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color(0xFFEAEDE7),
    onSurface = Color(0xFFEAEDE7),
)

@Composable
fun SiriDhanyaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors,
        typography = Typography(),
        content = content
    )
}
