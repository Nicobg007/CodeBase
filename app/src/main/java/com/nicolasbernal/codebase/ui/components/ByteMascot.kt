package com.nicolasbernal.codebase.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nicolasbernal.codebase.ui.theme.GreenDark
import com.nicolasbernal.codebase.ui.theme.GreenLight
import com.nicolasbernal.codebase.ui.theme.GreenPrimary
import com.nicolasbernal.codebase.ui.theme.OrangeStreak

enum class ByteMood { Happy, Sad, Cool }

/** Mascota Byte dibujada con Canvas (no necesita imágenes). */
@Composable
fun ByteMascot(
    modifier: Modifier = Modifier,
    mascotSize: Dp = 120.dp,
    mood: ByteMood = ByteMood.Happy,
    animate: Boolean = true,
) {
    val transition = rememberInfiniteTransition(label = "byte")
    val bob by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1100, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "bob",
    )
    val lift = if (animate) (-mascotSize.value * 0.05f * bob) else 0f

    Canvas(modifier.size(mascotSize).offset(y = lift.dp)) {
        val w = this.size.width
        val h = this.size.height
        val screen = Color(0xFF1E293B)

        // antena
        drawLine(GreenDark, Offset(w * 0.5f, h * 0.2f), Offset(w * 0.5f, h * 0.07f), strokeWidth = w * 0.035f, cap = StrokeCap.Round)
        drawCircle(OrangeStreak, radius = w * 0.055f, center = Offset(w * 0.5f, h * 0.07f))
        // orejas
        drawCircle(GreenDark, w * 0.075f, Offset(w * 0.07f, h * 0.56f))
        drawCircle(GreenDark, w * 0.075f, Offset(w * 0.93f, h * 0.56f))
        // cuerpo (con borde inferior)
        drawRoundRect(GreenDark, Offset(w * 0.08f, h * 0.24f), Size(w * 0.84f, h * 0.72f), CornerRadius(w * 0.26f))
        drawRoundRect(GreenPrimary, Offset(w * 0.08f, h * 0.2f), Size(w * 0.84f, h * 0.72f), CornerRadius(w * 0.26f))
        // pantalla
        drawRoundRect(screen, Offset(w * 0.18f, h * 0.32f), Size(w * 0.64f, h * 0.42f), CornerRadius(w * 0.16f))
        // ojos
        val eyeY = h * 0.47f
        if (mood == ByteMood.Cool) {
            drawRoundRect(GreenLight, Offset(w * 0.28f, eyeY - h * 0.03f), Size(w * 0.44f, h * 0.07f), CornerRadius(w * 0.03f))
        } else {
            drawCircle(GreenLight, w * 0.065f, Offset(w * 0.38f, eyeY))
            drawCircle(GreenLight, w * 0.065f, Offset(w * 0.62f, eyeY))
        }
        // boca
        val stroke = Stroke(width = w * 0.032f, cap = StrokeCap.Round)
        when (mood) {
            ByteMood.Happy, ByteMood.Cool -> drawArc(
                GreenLight, 20f, 140f, false,
                Offset(w * 0.4f, h * 0.5f), Size(w * 0.2f, h * 0.14f), style = stroke,
            )
            ByteMood.Sad -> drawArc(
                GreenLight, 200f, 140f, false,
                Offset(w * 0.4f, h * 0.6f), Size(w * 0.2f, h * 0.12f), style = stroke,
            )
        }
        // brillo
        drawRoundRect(Color.White.copy(alpha = 0.25f), Offset(w * 0.16f, h * 0.25f), Size(w * 0.22f, h * 0.04f), CornerRadius(w * 0.02f))
    }
}
