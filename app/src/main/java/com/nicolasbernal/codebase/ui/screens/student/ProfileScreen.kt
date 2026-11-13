package com.nicolasbernal.codebase.ui.screens.student

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolasbernal.codebase.data.SessionState
import com.nicolasbernal.codebase.ui.components.AvatarCircle
import com.nicolasbernal.codebase.ui.components.CodeCard
import com.nicolasbernal.codebase.ui.components.CodeProgressBar
import com.nicolasbernal.codebase.ui.components.SecondaryButton
import com.nicolasbernal.codebase.ui.components.SectionTitle
import com.nicolasbernal.codebase.ui.components.showToast
import com.nicolasbernal.codebase.ui.theme.TextSecondary

@Composable
// pestaña Perfil con estadísticas y ajustes
fun ProfileScreen(onLogout: () -> Unit) {
    val context = LocalContext.current
    val name = SessionState.userName.ifBlank { "Estudiante" }
    // controlan los diálogos de reiniciar y cambiar nombre
    var showReset by remember { mutableStateOf(false) }
    var showEditName by remember { mutableStateOf(false) }
    var draftName by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AvatarCircle("👩‍💻", size = 96.dp)
        Text(
            "$name ✏️",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.clickable {
                draftName = SessionState.userName
                showEditName = true
            },
        )
        Text("@${name.lowercase().replace(" ", "_")}_code", color = TextSecondary)

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatBox("${SessionState.streak}", "RACHA DE DÍAS", Modifier.weight(1f))
            StatBox("${SessionState.xp}", "TOTAL XP", Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatBox("Nivel ${SessionState.level}", SessionState.league, Modifier.weight(1f))
            StatBox("${SessionState.accuracy}%", "ACIERTOS", Modifier.weight(1f))
        }

        SectionTitle("Logros e Insignias", Modifier.align(Alignment.Start))
        SessionState.achievements().take(3).forEach { a ->
            CodeCard {
                Text("${a.emoji} ${a.title}", fontWeight = FontWeight.Bold)
                Text(a.description, color = TextSecondary, fontSize = 13.sp)
                CodeProgressBar(a.progress, modifier = Modifier.padding(top = 8.dp))
            }
        }

        SectionTitle("Ajustes Rápidos", Modifier.align(Alignment.Start))
        CodeCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("${SessionState.dailyGoalMin} min al día", fontWeight = FontWeight.Bold)
                    Text("Meta diaria", color = TextSecondary, fontSize = 13.sp)
                }
                Switch(
                    checked = SessionState.dailyGoalOn,
                    onCheckedChange = {
                        SessionState.dailyGoalOn = it
                        SessionState.save()
                    },
                )
            }
        }
        CodeCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Efectos de sonido", fontWeight = FontWeight.Bold)
                    Text("Al acertar y al fallar", color = TextSecondary, fontSize = 13.sp)
                }
                Switch(
                    checked = SessionState.soundOn,
                    onCheckedChange = {
                        SessionState.soundOn = it
                        SessionState.save()
                    },
                )
            }
        }
        SecondaryButton("REINICIAR PROGRESO", onClick = { showReset = true })
        SecondaryButton("CERRAR SESIÓN", onClick = onLogout)
    }

    // confirmación antes de borrar el progreso
    if (showReset) {
        AlertDialog(
            onDismissRequest = { showReset = false },
            title = { Text("¿Reiniciar progreso?") },
            text = { Text("Se borrarán tu XP, racha y lecciones completadas.") },
            confirmButton = {
                TextButton(onClick = {
                    SessionState.resetProgress()
                    showReset = false
                    showToast(context, "Progreso reiniciado")
                }) { Text("Reiniciar") }
            },
            dismissButton = { TextButton(onClick = { showReset = false }) { Text("Cancelar") } },
        )
    }

    // diálogo para cambiar el nombre
    if (showEditName) {
        AlertDialog(
            onDismissRequest = { showEditName = false },
            title = { Text("Tu nombre") },
            text = {
                OutlinedTextField(
                    value = draftName,
                    onValueChange = { draftName = it },
                    singleLine = true,
                    label = { Text("Nombre") },
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (draftName.isNotBlank()) {
                        SessionState.userName = draftName.trim()
                        SessionState.save()
                    }
                    showEditName = false
                }) { Text("Guardar") }
            },
            dismissButton = { TextButton(onClick = { showEditName = false }) { Text("Cancelar") } },
        )
    }
}

@Composable
// cajita con un número y su etiqueta
private fun StatBox(value: String, label: String, modifier: Modifier = Modifier) {
    CodeCard(modifier = modifier) {
        Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
        Text(label, color = TextSecondary, fontSize = 11.sp)
    }
}
