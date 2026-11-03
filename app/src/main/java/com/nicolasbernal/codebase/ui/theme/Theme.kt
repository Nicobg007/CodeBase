package com.nicolasbernal.codebase.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val CodeBaseColors = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.White,
    secondary = PurpleAccent,
    onSecondary = Color.White,
    background = ScreenBackground,
    onBackground = TextPrimary,
    surface = Color.White,
    onSurface = TextPrimary,
    error = ErrorRed,
    outline = BorderGray,
)

private val AppShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
)

@Composable
fun CodeBaseTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CodeBaseColors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content,
    )
}
