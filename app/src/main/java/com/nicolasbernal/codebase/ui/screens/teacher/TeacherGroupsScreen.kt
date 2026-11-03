package com.nicolasbernal.codebase.ui.screens.teacher

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
import com.nicolasbernal.codebase.data.MockData
import com.nicolasbernal.codebase.ui.components.AvatarCircle
import com.nicolasbernal.codebase.ui.components.CodeCard
import com.nicolasbernal.codebase.ui.components.CodeProgressBar
import com.nicolasbernal.codebase.ui.components.Pill
import com.nicolasbernal.codebase.ui.components.PrimaryButton
import com.nicolasbernal.codebase.ui.components.SectionTitle
import com.nicolasbernal.codebase.ui.theme.ErrorLight
import com.nicolasbernal.codebase.ui.theme.ErrorRed
import com.nicolasbernal.codebase.ui.theme.GreenDark
import com.nicolasbernal.codebase.ui.theme.PurpleAccent
import com.nicolasbernal.codebase.ui.theme.TextSecondary

@Composable
fun TeacherGroupsScreen(onLaunchChallenge: () -> Unit) {
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("¡Hola, Prof. Carlos! 👋", style = MaterialTheme.typography.headlineMedium)

        SectionTitle("MIS AULAS ASIGNADAS")
        MockData.groups.forEach { g ->
            CodeCard {
                Text(g.name, fontWeight = FontWeight.Bold)
                Text("${g.students} alumnos activos", color = TextSecondary, fontSize = 13.sp)
                CodeProgressBar(g.completion, modifier = Modifier.padding(top = 8.dp), color = GreenDark)
            }
        }

        SectionTitle("MÉTRICAS EN TIEMPO REAL")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Metric("6.4 días", "PROM. RACHA", Modifier.weight(1f))
            Metric("88%", "APROBACIÓN", Modifier.weight(1f))
        }

        SectionTitle("APOYO REQUERIDO")
        MockData.alerts.forEach { a ->
            CodeCard(borderColor = ErrorRed.copy(alpha = 0.4f), containerColor = ErrorLight.copy(alpha = 0.4f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AvatarCircle(a.name.take(2).uppercase(), size = 40.dp)
                    Column(Modifier.weight(1f)) {
                        Text(a.name, fontWeight = FontWeight.Bold)
                        Text(a.reason, color = TextSecondary, fontSize = 13.sp)
                    }
                    Pill("Alerta", ErrorRed, androidx.compose.ui.graphics.Color.White)
                }
            }
        }

        CodeCard(borderColor = PurpleAccent) {
            Text("¿Quieres activar la clase ahora?", fontWeight = FontWeight.Bold)
            Text("Lanza un desafío relámpago para toda la clase.", color = TextSecondary)
            PrimaryButton("LANZAR DESAFÍO RELÁMPAGO", onClick = onLaunchChallenge, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
private fun Metric(value: String, label: String, modifier: Modifier = Modifier) {
    CodeCard(modifier = modifier) {
        Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
        Text(label, color = TextSecondary, fontSize = 11.sp)
    }
}
