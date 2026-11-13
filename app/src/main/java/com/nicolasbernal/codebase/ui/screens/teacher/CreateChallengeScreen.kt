package com.nicolasbernal.codebase.ui.screens.teacher

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolasbernal.codebase.data.Question
import com.nicolasbernal.codebase.data.QuestionType
import com.nicolasbernal.codebase.data.StudentChallenge
import com.nicolasbernal.codebase.data.TeacherState
import com.nicolasbernal.codebase.data.TeacherTask
import com.nicolasbernal.codebase.ui.components.CodeBlock
import com.nicolasbernal.codebase.ui.components.CodeProgressBar
import com.nicolasbernal.codebase.ui.components.OptionCard
import com.nicolasbernal.codebase.ui.components.PrimaryButton
import com.nicolasbernal.codebase.ui.components.SectionTitle
import com.nicolasbernal.codebase.ui.components.showToast
import com.nicolasbernal.codebase.ui.theme.TextSecondary

@Composable
// pantalla para que el profe cree y publique un reto
fun CreateChallengeScreen(onBack: () -> Unit, onPublish: () -> Unit) {
    val context = LocalContext.current
    val types = listOf("Predice la salida", "Completar huecos", "Escribir código libre")

    // datos del reto que va escribiendo el profe
    var type by remember { mutableIntStateOf(0) }
    var title by remember { mutableStateOf("Desafío: Cajas fijas vs mutables") }
    var prompt by remember { mutableStateOf("¿Qué imprime este código?") }
    var code by remember { mutableStateOf("val vidas = 3\nval bonus = 2\nprintln(vidas + bonus)") }
    val options = remember { mutableStateListOf("5", "32", "6") }
    var correct by remember { mutableIntStateOf(0) }
    var points by remember { mutableIntStateOf(50) }

    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") }
            Text("Crear Desafío", fontWeight = FontWeight.Bold)
        }
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text("PASO 1 DE 2: CONFIGURAR RETO")
            CodeProgressBar(0.5f)

            SectionTitle("Tipo de Ejercicio")
            OptionCard(types[0], type == 0, { type = 0 }, "Analiza qué imprime el código de Kotlin")
            OptionCard(types[1], type == 1, { type = 1 }, "El alumno escribe la respuesta que falta")
            OptionCard(types[2], type == 2, { type = 2 }, "Opción múltiple sobre un fragmento de código")

            SectionTitle("Detalles del Desafío")
            OutlinedTextField(
                value = title, onValueChange = { title = it },
                label = { Text("Título del reto") }, singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = prompt, onValueChange = { prompt = it },
                label = { Text("Pregunta para el alumno") }, singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = code, onValueChange = { code = it },
                label = { Text("Código en Kotlin") }, minLines = 3,
                modifier = Modifier.fillMaxWidth(),
            )
            if (code.isNotBlank()) CodeBlock(code)

            SectionTitle("Opciones del alumno (marca la correcta)")
            if (type == 1) {
                Text("El alumno escribirá el texto de la opción marcada.", color = TextSecondary, fontSize = 13.sp)
            }
            // cada opción tiene un radio para marcar la correcta
            options.forEachIndexed { i, text ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = correct == i, onClick = { correct = i })
                    OutlinedTextField(
                        value = text,
                        onValueChange = { options[i] = it },
                        label = { Text("Opción ${'A' + i}") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            SectionTitle("Recompensa y Entrega")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(25, 50, 100).forEach { p ->
                    FilterChip(selected = points == p, onClick = { points = p }, label = { Text("$p XP") })
                }
            }
            Text("Fecha límite: Próxima clase (Viernes 18:00)")
        }
        PrimaryButton(
            "PUBLICAR RETO AL GRUPO",
            onClick = {
                // validamos que no haya campos vacíos
                val cleaned = options.map { it.trim() }
                if (title.isBlank() || prompt.isBlank() || cleaned.any { it.isBlank() }) {
                    showToast(context, "Completa el título, la pregunta y las 3 opciones")
                } else {
                    val answer = cleaned[correct]
                    // completar huecos genera una pregunta de escribir, los otros tipos de opción múltiple
                    val question = if (type == 1) {
                        Question(
                            type = QuestionType.FILL,
                            prompt = prompt.trim(),
                            code = code,
                            options = listOf(answer),
                            explanation = "La respuesta correcta es $answer.",
                        )
                    } else {
                        Question(
                            prompt = prompt.trim(),
                            code = code,
                            options = cleaned,
                            correctIndex = correct,
                            explanation = "La respuesta correcta es $answer.",
                        )
                    }
                    // guardamos el reto y se lo mandamos al alumno
                    TeacherState.publish(
                        TeacherTask(title.trim(), "${types[type]} · $points XP", 0, 28, "NUEVO"),
                        StudentChallenge(title.trim(), points, question),
                    )
                    showToast(context, "¡Reto publicado! 🚀")
                    onPublish()
                }
            },
            modifier = Modifier.padding(20.dp),
        )
    }
}
