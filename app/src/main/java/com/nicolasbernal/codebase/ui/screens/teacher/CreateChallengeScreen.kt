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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nicolasbernal.codebase.ui.components.CodeBlock
import com.nicolasbernal.codebase.ui.components.CodeProgressBar
import com.nicolasbernal.codebase.ui.components.OptionCard
import com.nicolasbernal.codebase.ui.components.PrimaryButton
import com.nicolasbernal.codebase.ui.components.SectionTitle

@Composable
fun CreateChallengeScreen(onBack: () -> Unit, onPublish: () -> Unit) {
    var type by remember { mutableIntStateOf(0) }
    var topic by remember { mutableStateOf("Variables (val vs var)") }
    var points by remember { mutableIntStateOf(50) }
    var correct by remember { mutableIntStateOf(0) }

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
            OptionCard("Predice la salida", type == 0, { type = 0 }, "Analiza qué imprime el código de Kotlin")
            OptionCard("Completar huecos", type == 1, { type = 1 }, "Rellenar con val, var o tipos de datos")
            OptionCard("Escribir código libre", type == 2, { type = 2 }, "Mini-función evaluada por tests")

            SectionTitle("Detalles del Desafío")
            OutlinedTextField(
                value = topic, onValueChange = { topic = it },
                label = { Text("Tema curricular") }, singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            CodeBlock("val vidas = 3\nval bonus = 2\nval total = vidas + bonus\n// ¿Qué imprime println(total)?")

            SectionTitle("Opciones del alumno (toca para marcar la correcta)")
            listOf("5", "32", "6").forEachIndexed { i, text ->
                OptionCard(text, correct == i, { correct = i }, if (correct == i) "Correcta" else "Incorrecta")
            }

            SectionTitle("Recompensa y Entrega")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(25, 50, 100).forEach { p ->
                    FilterChip(selected = points == p, onClick = { points = p }, label = { Text("$p XP") })
                }
            }
            Text("Fecha límite: Próxima clase (Viernes 18:00)")
        }
        PrimaryButton("PUBLICAR RETO AL GRUPO", onClick = onPublish, modifier = Modifier.padding(20.dp))
    }
}
