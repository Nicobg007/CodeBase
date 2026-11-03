package com.nicolasbernal.codebase.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nicolasbernal.codebase.ui.theme.BorderGray
import com.nicolasbernal.codebase.ui.theme.GreenPrimary

@Composable
fun CodeProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = GreenPrimary,
    height: Dp = 10.dp,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(50))
            .background(BorderGray),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .fillMaxHeight()
                .clip(RoundedCornerShape(50))
                .background(color),
        )
    }
}
