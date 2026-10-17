package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = TalabnaCyan,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF003866),
    onPrimaryContainer = TalabnaCyanBright,
    secondary = TalabnaSilver,
    onSecondary = Color(0xFF0F172A),
    secondaryContainer = TalabnaSurfaceVariant,
    onSecondaryContainer = TalabnaSilverLight,
    tertiary = TalabnaCyanBright,
    onTertiary = Color.Black,
    background = TalabnaDarkBg,
    onBackground = TalabnaSilverLight,
    surface = TalabnaSurface,
    onSurface = TalabnaSilverLight,
    surfaceVariant = TalabnaSurfaceVariant,
    onSurfaceVariant = TalabnaSilverDark,
    outline = TalabnaBorder,
    outlineVariant = Color(0xFF1E293B)
)

@Composable
fun TalabnaXTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
