package com.nicolasbernal.codebase.ui.screens.student

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nicolasbernal.codebase.ui.components.CodeBlock
import com.nicolasbernal.codebase.ui.components.CodeCard
import com.nicolasbernal.codebase.ui.components.PrimaryButton
import com.nicolasbernal.codebase.ui.theme.PurpleAccent
import com.nicolasbernal.codebase.ui.theme.PurpleLight

@Composable
fun LessonScreen(onBack: () -> Unit, onContinue: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") }
            Text("Lección · Variables y cajas", fontWeight = FontWeight.Bold)
        }
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("¿Cómo funciona una variable?", style = MaterialTheme.typography.headlineMedium)
            Text(
                "El concepto fundamental para empezar a programar en CodeBase. En Kotlin usamos val para cajas " +
                    "fijas que no cambian y var para cajas cuyo contenido puede modificarse.",
            )
            CodeBlock(
                """
                fun main() {
                    val nombre = "Alex"
                    var puntos = 100
                    println("Hola Alex")
                }
                """.trimIndent(),
            )
            CodeCard(borderColor = PurpleAccent, containerColor = PurpleLight) {
                Text("¡Regla de oro de Kotlin!", fontWeight = FontWeight.Bold)
                Text("Usa siempre val por defecto y el valor no cambiará. Usa var solo cuando realmente necesites reasignarlo.")
            }
        }
        PrimaryButton("ENTENDIDO →", onClick = onContinue, modifier = Modifier.padding(20.dp))
    }
}
