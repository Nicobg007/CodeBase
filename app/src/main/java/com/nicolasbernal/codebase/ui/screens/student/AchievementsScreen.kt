package com.nicolasbernal.codebase.ui.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolasbernal.codebase.data.MockData
import com.nicolasbernal.codebase.data.RankEntry
import com.nicolasbernal.codebase.data.SessionState
import com.nicolasbernal.codebase.ui.components.CodeCard
import com.nicolasbernal.codebase.ui.components.CodeProgressBar
import com.nicolasbernal.codebase.ui.components.Pill
import com.nicolasbernal.codebase.ui.components.SectionTitle
import com.nicolasbernal.codebase.ui.theme.BorderGray
import com.nicolasbernal.codebase.ui.theme.GreenDark
import com.nicolasbernal.codebase.ui.theme.GreenLight
import com.nicolasbernal.codebase.ui.theme.GreenPrimary
import com.nicolasbernal.codebase.ui.theme.TextSecondary

@Composable
// pestaña Logros: liga semanal e insignias
fun AchievementsScreen() {
    val achievements = SessionState.achievements()
    val unlocked = achievements.count { it.done }
    // nos metemos en la tabla con nuestro XP y ordenamos de mayor a menor
    val me = RankEntry(SessionState.userName.ifBlank { "Tú" }, SessionState.xp)
    val board = (MockData.leaderboard + me).sortedByDescending { it.xp }
    val myPosition = board.indexOfFirst { it === me } + 1

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Logros e Insignias", style = MaterialTheme.typography.headlineMedium)

        SectionTitle("LIGA SEMANAL · ${SessionState.league}")
        CodeCard {
            Text("Vas en el puesto $myPosition de ${board.size}", fontWeight = FontWeight.Bold)
            Column(Modifier.padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                board.forEachIndexed { i, entry ->
                    val mine = entry === me
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (mine) GreenLight else Color.Transparent)
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            when (i) {
                                0 -> "🥇"
                                1 -> "🥈"
                                2 -> "🥉"
                                else -> "${i + 1}"
                            },
                            modifier = Modifier.width(34.dp),
                            fontSize = 16.sp,
                        )
                        Text(
                            if (mine) "${entry.name} (tú)" else entry.name,
                            fontWeight = if (mine) FontWeight.ExtraBold else FontWeight.Medium,
                            modifier = Modifier.weight(1f),
                        )
                        Text("${entry.xp} XP", color = if (mine) GreenDark else TextSecondary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        SectionTitle("INSIGNIAS · $unlocked de ${achievements.size} desbloqueadas")
        // cada insignia con su barra de progreso
        achievements.forEach { a ->
            CodeCard(
                borderColor = if (a.done) GreenPrimary else BorderGray,
                containerColor = if (a.done) GreenLight.copy(alpha = 0.35f) else Color.White,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(a.emoji, fontSize = 28.sp)
                    Column(Modifier.weight(1f)) {
                        Text(a.title, fontWeight = FontWeight.Bold)
                        Text(a.description, color = TextSecondary, fontSize = 13.sp)
                    }
                    if (a.done) Pill("Completado", GreenPrimary, Color.White)
                }
                CodeProgressBar(a.progress, modifier = Modifier.padding(top = 10.dp), color = GreenDark)
            }
        }
    }
}
