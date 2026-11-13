package com.nicolasbernal.codebase.ui.screens.teacher

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolasbernal.codebase.data.TeacherState
import com.nicolasbernal.codebase.data.TeacherTask
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

// categorías del filtro
private val filterNames = listOf("Todos", "Activas", "Programadas", "Completadas")

// dice si un reto entra en el filtro elegido
private fun matches(task: TeacherTask, filter: Int): Boolean = when (filter) {
    1 -> task.badge == "RETO RÁPIDO" || task.badge == "NUEVO" || task.badge == "PUBLICADO"
    2 -> task.badge == "POR PUBLICAR"
    3 -> task.badge == "COMPLETADA"
    else -> true
}

@Composable
// pestaña Tareas y retos
fun TeacherTasksScreen(onNewTask: () -> Unit, onSeeSubmissions: (String) -> Unit) {
    val context = LocalContext.current
    var filter by remember { mutableIntStateOf(0) }
    // guardamos la posición real del reto para poder editarlo
    val visible = TeacherState.tasks.withIndex().filter { matches(it.value, filter) }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Tareas y Retos", style = MaterialTheme.typography.headlineMedium)
        PrimaryButton("+ ASIGNAR NUEVA TAREA O RETO", onClick = onNewTask)

        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            filterNames.forEachIndexed { i, name ->
                val count = TeacherState.tasks.count { matches(it, i) }
                FilterChip(selected = filter == i, onClick = { filter = i }, label = { Text("$name ($count)") })
            }
        }

        if (visible.isEmpty()) {
            Text("No hay retos en esta categoría", color = TextSecondary)
        }

        // tarjeta de cada reto
        visible.forEach { (i, t) ->
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
                // los programados se pueden publicar desde aquí
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
