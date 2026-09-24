package com.example.myapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = LiceoMaroonLight,
    secondary = LiceoGold,
    tertiary = LiceoMaroon,
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF25232A),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color(0xFFE6E1E5),
    onSurface = Color(0xFFE6E1E5)
)

private val LightColorScheme = lightColorScheme(
    primary = LiceoMaroon,
    secondary = LiceoGold,
    tertiary = LiceoMaroonDark,
    background = LiceoOffWhite,
    surface = LiceoSurface,
    onPrimary = OnLiceoMaroon,
    onSecondary = Color.Black,
    onBackground = OnLiceoOffWhite,
    onSurface = OnLiceoOffWhite,
    primaryContainer = Color(0xFFF3E5E5),
    onPrimaryContainer = LiceoMaroonDark
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Alias for LiceoAccountTheme if referenced anywhere
@Composable
fun LiceoAccountTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MyApplicationTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor,
        content = content
    )
}
