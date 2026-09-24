package edu.liceo.fieldkit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LiceoMaroon = Color(0xFF800000)
val LiceoMaroonLight = Color(0xFFA32A2A)
val LiceoGold = Color(0xFFDAA520)
val LiceoOffWhite = Color(0xFFFAF9F6)

private val DarkColorScheme = darkColorScheme(
    primary = LiceoMaroonLight,
    secondary = LiceoGold,
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF25232A)
)

private val LightColorScheme = lightColorScheme(
    primary = LiceoMaroon,
    secondary = LiceoGold,
    background = LiceoOffWhite,
    surface = Color.White
)

@Composable
fun LiceoFieldKitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
