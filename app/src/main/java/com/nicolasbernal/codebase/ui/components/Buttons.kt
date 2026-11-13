package com.nicolasbernal.codebase.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nicolasbernal.codebase.ui.theme.BorderGray
import com.nicolasbernal.codebase.ui.theme.GreenPrimary
import com.nicolasbernal.codebase.ui.theme.TextPrimary

@Composable
// botón verde con efecto 3D: tiene un borde abajo y se hunde al presionar
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = GreenPrimary,
    enabled: Boolean = true,
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val depth = 5.dp
    // cuánto baja el botón mientras está presionado
    val shift by animateDpAsState(if (pressed && enabled) depth else 0.dp, tween(80), label = "press")
    val face = if (enabled) color else BorderGray
    val edge = if (enabled) lerp(color, Color.Black, 0.22f) else Color(0xFFCBD5E1)
    val shape = RoundedCornerShape(16.dp)

    // capa de abajo (el borde) y capa de arriba (la cara del botón)
    Box(modifier.fillMaxWidth().height(52.dp + depth)) {
        Box(
            Modifier.fillMaxWidth().height(52.dp).align(Alignment.BottomCenter)
                .clip(shape).background(edge),
        )
        Box(
            Modifier.fillMaxWidth().height(52.dp).align(Alignment.TopCenter)
                .offset(y = shift)
                .clip(shape).background(face)
                .clickable(interactionSource = interaction, indication = null, enabled = enabled, onClick = onClick),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text,
                color = if (enabled) Color.White else Color(0xFF94A3B8),
                fontWeight = FontWeight.ExtraBold,
            )
        }
    }
}

@Composable
// botón blanco con borde para acciones secundarias
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, BorderGray),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
    ) {
        Text(text, fontWeight = FontWeight.Bold)
    }
}
