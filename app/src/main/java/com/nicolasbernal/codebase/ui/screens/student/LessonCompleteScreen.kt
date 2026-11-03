package com.nicolasbernal.codebase.ui.screens.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nicolasbernal.codebase.data.SessionState
import com.nicolasbernal.codebase.ui.components.AvatarCircle
import com.nicolasbernal.codebase.ui.components.CodeCard
import com.nicolasbernal.codebase.ui.components.PrimaryButton
import com.nicolasbernal.codebase.ui.theme.GreenDark
import com.nicolasbernal.codebase.ui.theme.TextSecondary

@Composable
fun LessonCompleteScreen(onContinue: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        AvatarCircle("🎉", size = 110.dp)
        Spacer(Modifier.height(16.dp))
        Text("¡Lección completada! 🎉", style = MaterialTheme.typography.headlineMedium)
        Text("Has dominado los conceptos de Variables y Asignación.", color = TextSecondary, modifier = Modifier.padding(8.dp))
        Spacer(Modifier.height(16.dp))

        ResultRow("⚡", "+${SessionState.lastGainedXp} XP", "Puntos ganados")
        Spacer(Modifier.height(8.dp))
        ResultRow("🔥", "¡Racha de ${SessionState.streak} días!", "Sigue así")
        Spacer(Modifier.height(8.dp))
        ResultRow("🎯", "${SessionState.lastAccuracy}% Precisión", "Respuestas correctas")
        Spacer(Modifier.height(24.dp))

        PrimaryButton("CONTINUAR", onClick = onContinue)
    }
}

@Composable
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
