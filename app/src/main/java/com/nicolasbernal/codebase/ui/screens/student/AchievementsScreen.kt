package com.nicolasbernal.codebase.ui.screens.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolasbernal.codebase.data.SessionState
import com.nicolasbernal.codebase.ui.components.CodeCard
import com.nicolasbernal.codebase.ui.components.CodeProgressBar
import com.nicolasbernal.codebase.ui.components.Pill
import com.nicolasbernal.codebase.ui.theme.GreenDark
import com.nicolasbernal.codebase.ui.theme.GreenLight
import com.nicolasbernal.codebase.ui.theme.GreenPrimary
import com.nicolasbernal.codebase.ui.theme.TextSecondary

/** Pestaña "Logros": se calculan solos con tu progreso real. */
@Composable
fun AchievementsScreen() {
    val achievements = SessionState.achievements()
    val unlocked = achievements.count { it.done }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Logros e Insignias", style = MaterialTheme.typography.headlineMedium)
        Text("$unlocked de ${achievements.size} desbloqueados", color = TextSecondary)

        achievements.forEach { a ->
            CodeCard(
                borderColor = if (a.done) GreenPrimary else com.nicolasbernal.codebase.ui.theme.BorderGray,
                containerColor = if (a.done) GreenLight.copy(alpha = 0.35f) else androidx.compose.ui.graphics.Color.White,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(a.emoji, fontSize = 28.sp)
                    Column(Modifier.weight(1f)) {
                        Text(a.title, fontWeight = FontWeight.Bold)
                        Text(a.description, color = TextSecondary, fontSize = 13.sp)
                    }
                    if (a.done) Pill("Completado", GreenPrimary, androidx.compose.ui.graphics.Color.White)
                }
                CodeProgressBar(a.progress, modifier = Modifier.padding(top = 10.dp), color = GreenDark)
            }
        }
    }
}
