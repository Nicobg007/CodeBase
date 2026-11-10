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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolasbernal.codebase.data.TeacherState
import com.nicolasbernal.codebase.ui.components.CodeCard
import com.nicolasbernal.codebase.ui.components.CodeProgressBar
import com.nicolasbernal.codebase.ui.components.Pill
import com.nicolasbernal.codebase.ui.components.PrimaryButton
import com.nicolasbernal.codebase.ui.components.SecondaryButton
import com.nicolasbernal.codebase.ui.components.showToast
import com.nicolasbernal.codebase.ui.theme.GreenDark
import com.nicolasbernal.codebase.ui.theme.PurpleAccent
import com.nicolasbernal.codebase.ui.theme.PurpleLight
import com.nicolasbernal.codebase.ui.theme.TextSecondary

@Composable
fun TeacherTasksScreen(onNewTask: () -> Unit, onSeeSubmissions: (String) -> Unit) {
    val context = LocalContext.current

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Tareas y Retos", style = MaterialTheme.typography.headlineMedium)
        PrimaryButton("+ ASIGNAR NUEVA TAREA O RETO", onClick = onNewTask)

        TeacherState.tasks.forEachIndexed { i, t ->
            CodeCard {
                Row { Pill(t.badge, PurpleLight, PurpleAccent) }
                Text(t.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                Text(t.subtitle, color = TextSecondary, fontSize = 13.sp)
                Text("${t.delivered} / ${t.total} entregados", fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
                CodeProgressBar(
                    progress = t.delivered.toFloat() / t.total,
                    modifier = Modifier.padding(top = 4.dp),
                    color = GreenDark,
                )
                if (t.badge == "POR PUBLICAR") {
                    PrimaryButton(
                        "PUBLICAR AHORA",
                        onClick = {
                            TeacherState.tasks[i] = t.copy(badge = "PUBLICADO", subtitle = "Publicado ahora")
                            showToast(context, "¡Publicado al grupo!")
                        },
                        modifier = Modifier.padding(top = 12.dp),
                    )
                } else {
                    SecondaryButton("Ver entregas", onClick = { onSeeSubmissions(t.title) }, modifier = Modifier.padding(top = 12.dp))
                }
            }
        }
    }
}
