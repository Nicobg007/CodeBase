package com.nicolasbernal.codebase.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// TODO: cuando tengas la fuente del diseño (ej. Nunito / Plus Jakarta Sans),
// agrégala en res/font y ponla como fontFamily aquí.
val AppTypography = Typography(
    headlineMedium = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 26.sp, lineHeight = 32.sp),
    titleLarge = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, lineHeight = 28.sp),
    titleMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, lineHeight = 22.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 15.sp),
)
