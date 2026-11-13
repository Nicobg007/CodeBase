package com.nicolasbernal.codebase.ui.screens.student

// atrás aquí vuelve a la ruta y no al ejercicio
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nicolasbernal.codebase.data.SessionState
import com.nicolasbernal.codebase.ui.components.ByteMascot
import com.nicolasbernal.codebase.ui.components.ByteMood
import com.nicolasbernal.codebase.ui.components.CodeCard
import com.nicolasbernal.codebase.ui.components.Confetti
import com.nicolasbernal.codebase.ui.components.PrimaryButton
import com.nicolasbernal.codebase.ui.components.SoundFx
import com.nicolasbernal.codebase.ui.theme.GreenDark
import com.nicolasbernal.codebase.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
// pantalla 7: lección completada con confeti
fun LessonCompleteScreen(onContinue: () -> Unit) {
    // las tarjetas aparecen una por una
    var step by remember { mutableIntStateOf(0) }
    BackHandler { onContinue() }
    LaunchedEffect(Unit) {
        SoundFx.win()
        repeat(3) {
            delay(400)
            step += 1
        }
    }

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            ByteMascot(mascotSize = 130.dp, mood = ByteMood.Cool)
            Spacer(Modifier.height(16.dp))
            Text(
                "¡Lección completada! 🎉",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
            )
            Text(
                "Has dominado: ${SessionState.lastLessonTitle}.",
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp),
            )
            Spacer(Modifier.height(16.dp))

            AnimatedVisibility(step >= 1, enter = fadeIn(tween(350)) + slideInVertically(tween(350)) { it / 2 }) {
                ResultRow("⚡", "+${SessionState.lastGainedXp} XP", "Puntos ganados")
            }
            Spacer(Modifier.height(8.dp))
            AnimatedVisibility(step >= 2, enter = fadeIn(tween(350)) + slideInVertically(tween(350)) { it / 2 }) {
                ResultRow("🔥", "¡Racha de ${SessionState.streak} días!", "Sigue así")
            }
            Spacer(Modifier.height(8.dp))
            AnimatedVisibility(step >= 3, enter = fadeIn(tween(350)) + slideInVertically(tween(350)) { it / 2 }) {
                ResultRow("🎯", "${SessionState.lastAccuracy}% Precisión", "Respuestas correctas")
            }
            Spacer(Modifier.height(24.dp))

            PrimaryButton("CONTINUAR", onClick = onContinue)
        }
        // el confeti va encima de todo
        Confetti(Modifier.fillMaxSize())
    }
}

@Composable
// fila de resultado (XP, racha, precisión)
private fun ResultRow(emoji: String, title: String, subtitle: String) {
    CodeCard {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(emoji)
            Column(Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.ExtraBold, color = GreenDark)
                Text(subtitle, color = TextSecondary)
            }
        }
    }
}
