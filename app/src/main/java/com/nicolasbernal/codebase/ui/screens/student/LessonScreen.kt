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
import androidx.compose.ui.unit.sp
import com.nicolasbernal.codebase.data.MockData
import com.nicolasbernal.codebase.ui.components.ByteMascot
import com.nicolasbernal.codebase.ui.components.CodeBlock
import com.nicolasbernal.codebase.ui.components.CodeCard
import com.nicolasbernal.codebase.ui.components.PrimaryButton
import com.nicolasbernal.codebase.ui.components.SectionTitle
import com.nicolasbernal.codebase.ui.theme.BlueXp
import com.nicolasbernal.codebase.ui.theme.GreenDark
import com.nicolasbernal.codebase.ui.theme.GreenLight
import com.nicolasbernal.codebase.ui.theme.PurpleAccent
import com.nicolasbernal.codebase.ui.theme.PurpleLight
import com.nicolasbernal.codebase.ui.theme.TextSecondary

@Composable
// pantalla 5: teoría de la lección antes del ejercicio
fun LessonScreen(lessonIndex: Int, onBack: () -> Unit, onContinue: () -> Unit) {
    val index = lessonIndex.coerceIn(0, MockData.lessons.lastIndex)
    // buscamos la lección por su posición
    val lesson = MockData.lessons[index]

    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") }
            Text("Lección ${index + 1} · ${lesson.title}", fontWeight = FontWeight.Bold)
        }
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(lesson.headline, style = MaterialTheme.typography.headlineMedium)

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ByteMascot(mascotSize = 64.dp)
                CodeCard(modifier = Modifier.weight(1f)) { Text(lesson.explanation) }
            }

            // la metáfora de las cajas solo aplica a la primera lección
            if (index == 0) {
                SectionTitle("METÁFORA VISUAL")
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    CodeCard(Modifier.weight(1f), borderColor = GreenDark, containerColor = GreenLight.copy(alpha = 0.4f)) {
                        Text("🔒 val", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                        Text("Caja cerrada: el valor no cambia", color = TextSecondary, fontSize = 13.sp)
                    }
                    CodeCard(Modifier.weight(1f), borderColor = BlueXp) {
                        Text("📦 var", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                        Text("Caja abierta: puedes cambiarlo", color = TextSecondary, fontSize = 13.sp)
                    }
                }
            }

            SectionTitle("EJEMPLO")
            CodeBlock(lesson.example)

            CodeCard(borderColor = PurpleAccent, containerColor = PurpleLight) {
                Text("¡Regla de oro de Kotlin!", fontWeight = FontWeight.Bold)
                Text(lesson.tip)
            }
        }
        PrimaryButton("ENTENDIDO →", onClick = onContinue, modifier = Modifier.padding(20.dp))
    }
}
