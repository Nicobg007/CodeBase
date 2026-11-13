package com.nicolasbernal.codebase.ui.screens.student

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nicolasbernal.codebase.data.SessionState
import com.nicolasbernal.codebase.ui.components.ByteMascot
import com.nicolasbernal.codebase.ui.components.CodeProgressBar
import com.nicolasbernal.codebase.ui.components.OptionCard
import com.nicolasbernal.codebase.ui.components.PrimaryButton
import com.nicolasbernal.codebase.ui.components.SectionTitle

@Composable
// pantalla 3: preguntas para personalizar el plan
fun OnboardingScreen(onFinish: () -> Unit) {
    // respuestas elegidas (posición de la opción)
    var level by remember { mutableIntStateOf(1) }
    var goal by remember { mutableIntStateOf(1) }
    var language by remember { mutableIntStateOf(0) }

    // opciones de cada pregunta: título y descripción
    val levels = listOf("Nunca he programado" to "Desde absoluto cero", "Sé un poco" to "Conozco variables y lógica básica")
    val goals = listOf("5 min" to "Casual", "10 min" to "Recomendado", "15 min" to "Intenso")
    val languages = listOf("Kotlin" to "Más popular", "Android" to "Apps móviles", "Backend" to "Kotlin y APIs")

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        CodeProgressBar(progress = 0.66f)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "¡Vamos a personalizar tu plan!",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f),
            )
            ByteMascot(mascotSize = 64.dp)
        }

        SectionTitle("1. ¿Cuánto sabes de código?")
        levels.forEachIndexed { i, (t, s) -> OptionCard(t, level == i, { level = i }, subtitle = s) }

        SectionTitle("2. Tu meta diaria de estudio")
        goals.forEachIndexed { i, (t, s) -> OptionCard(t, goal == i, { goal = i }, subtitle = s) }

        SectionTitle("3. ¿Qué lenguaje quieres aprender primero?")
        languages.forEachIndexed { i, (t, s) -> OptionCard(t, language == i, { language = i }, subtitle = s) }

        PrimaryButton(
            "CONTINUAR",
            onClick = {
                // guardamos la meta diaria que eligió
                SessionState.dailyGoalMin = listOf(5, 10, 15)[goal]
                onFinish()
            },
            modifier = Modifier.padding(top = 12.dp),
        )
    }
}
