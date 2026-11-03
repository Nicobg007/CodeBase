package com.nicolasbernal.codebase.ui.screens.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolasbernal.codebase.data.MockData
import com.nicolasbernal.codebase.data.SessionState
import com.nicolasbernal.codebase.ui.components.AvatarCircle
import com.nicolasbernal.codebase.ui.components.CodeCard
import com.nicolasbernal.codebase.ui.components.CodeProgressBar
import com.nicolasbernal.codebase.ui.components.SecondaryButton
import com.nicolasbernal.codebase.ui.components.SectionTitle
import com.nicolasbernal.codebase.ui.theme.TextSecondary

@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    var dailyGoal by remember { mutableStateOf(true) }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AvatarCircle("👩‍💻", size = 96.dp)
        Text("Sofía Martínez", style = MaterialTheme.typography.titleLarge)
        Text("@sofia_code", color = TextSecondary)

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatBox("${SessionState.streak}", "RACHA DE DÍAS", Modifier.weight(1f))
            StatBox("${SessionState.xp}", "TOTAL XP", Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatBox("Nivel 2", "LIGA BRONCE", Modifier.weight(1f))
            StatBox("94%", "ACIERTOS", Modifier.weight(1f))
        }

        SectionTitle("Logros e Insignias", Modifier.align(Alignment.Start))
        MockData.achievements.forEach { a ->
            CodeCard {
                Text(a.title, fontWeight = FontWeight.Bold)
                Text(a.description, color = TextSecondary, fontSize = 13.sp)
                CodeProgressBar(a.progress, modifier = Modifier.padding(top = 8.dp))
            }
        }

        SectionTitle("Ajustes Rápidos", Modifier.align(Alignment.Start))
        CodeCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("10 min al día", fontWeight = FontWeight.Bold)
                    Text("Meta diaria", color = TextSecondary, fontSize = 13.sp)
                }
                Switch(checked = dailyGoal, onCheckedChange = { dailyGoal = it })
            }
        }
        SecondaryButton("CERRAR SESIÓN", onClick = onLogout)
    }
}

@Composable
private fun StatBox(value: String, label: String, modifier: Modifier = Modifier) {
    CodeCard(modifier = modifier) {
        Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
        Text(label, color = TextSecondary, fontSize = 11.sp)
    }
}
