package com.nicolasbernal.codebase.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import com.nicolasbernal.codebase.ui.theme.BlueXp
import com.nicolasbernal.codebase.ui.theme.GreenPrimary
import com.nicolasbernal.codebase.ui.theme.OrangeStreak
import com.nicolasbernal.codebase.ui.theme.PurpleAccent
import kotlin.math.sin
import kotlin.random.Random

// una piecita de confeti
private class Particle(val x: Float, val speed: Float, val size: Float, val color: Color, val phase: Float)

@Composable
// confeti que cae una sola vez al completar la lección
fun Confetti(modifier: Modifier = Modifier) {
    val colors = listOf(GreenPrimary, PurpleAccent, OrangeStreak, BlueXp)
    // creamos 70 piezas con posición, velocidad y color al azar
    val particles = remember {
        List(70) {
            Particle(
                x = Random.nextFloat(),
                speed = 0.6f + Random.nextFloat() * 0.8f,
                size = 10f + Random.nextFloat() * 12f,
                color = colors[Random.nextInt(colors.size)],
                phase = Random.nextFloat() * 6f,
            )
        }
    }
    // va de 0 a 1 en 3 segundos y con eso movemos todo
    val progress = remember { Animatable(0f) }
    LaunchedEffect(Unit) { progress.animateTo(1f, tween(3200, easing = LinearEasing)) }

    // dibujamos cada pieza cayendo y girando
    Canvas(modifier) {
        val t = progress.value
        particles.forEach { p ->
            val y = (-0.08f + t * p.speed * 1.25f) * this.size.height
            val x = p.x * this.size.width + sin(t * 8f + p.phase) * 24f
            if (y < this.size.height) {
                rotate(degrees = t * 540f * p.speed, pivot = Offset(x, y)) {
                    drawRect(p.color, topLeft = Offset(x, y), size = Size(p.size, p.size * 0.6f))
                }
            }
        }
    }
}
