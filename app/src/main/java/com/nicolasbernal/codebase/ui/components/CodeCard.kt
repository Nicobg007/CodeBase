package com.nicolasbernal.codebase.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nicolasbernal.codebase.ui.theme.BorderGray

/** Tarjeta base de la app (borde gris suave, esquinas redondeadas). */
@Composable
fun CodeCard(
    modifier: Modifier = Modifier,
    borderColor: Color = BorderGray,
    containerColor: Color = Color.White,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(16.dp)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(containerColor)
            .border(1.5.dp, borderColor, shape)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(16.dp),
        content = content,
    )
}
