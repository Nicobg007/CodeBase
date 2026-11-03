package com.nicolasbernal.codebase.ui.screens.teacher

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolasbernal.codebase.data.MockData
import com.nicolasbernal.codebase.ui.components.AvatarCircle
import com.nicolasbernal.codebase.ui.components.CodeCard
import com.nicolasbernal.codebase.ui.components.CodeProgressBar
import com.nicolasbernal.codebase.ui.components.Pill
import com.nicolasbernal.codebase.ui.components.PrimaryButton
import com.nicolasbernal.codebase.ui.theme.BlueXp
import com.nicolasbernal.codebase.ui.theme.ErrorLight
import com.nicolasbernal.codebase.ui.theme.ErrorRed
import com.nicolasbernal.codebase.ui.theme.GreenDark
import com.nicolasbernal.codebase.ui.theme.GreenLight
import com.nicolasbernal.codebase.ui.theme.TextSecondary

@Composable
fun SubmissionsScreen(onBack: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") }
            Text("Entregas", fontWeight = FontWeight.Bold)
        }
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text("Cajas fijas vs mutables", style = MaterialTheme.typography.headlineMedium)
            Text("22 de 28 estudiantes completaron · 78%", color = TextSecondary)
            CodeProgressBar(0.78f, color = GreenDark)

            MockData.submissions.forEach { s ->
                val bad = s.status == "Sin entrega"
                CodeCard(containerColor = if (bad) ErrorLight.copy(alpha = 0.5f) else Color.White) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        AvatarCircle(s.name.take(1), size = 40.dp, background = if (bad) ErrorLight else GreenLight)
                        Column(Modifier.weight(1f)) {
                            Text(s.name, fontWeight = FontWeight.Bold)
                            Text(s.detail, color = TextSecondary, fontSize = 12.sp)
                        }
                        if (s.xp != null) Text("+${s.xp} XP", color = BlueXp, fontWeight = FontWeight.Bold)
                    }
                    Pill(
                        s.status,
                        if (bad) ErrorRed else GreenLight,
                        if (bad) Color.White else GreenDark,
                        Modifier.padding(top = 8.dp),
                    )
                }
            }
        }
        PrimaryButton("ENVIAR FELICITACIÓN GRUPAL", onClick = { /* TODO */ }, modifier = Modifier.padding(20.dp))
    }
}
