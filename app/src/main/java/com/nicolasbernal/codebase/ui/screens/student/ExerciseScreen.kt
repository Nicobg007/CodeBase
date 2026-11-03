package com.nicolasbernal.codebase.ui.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nicolasbernal.codebase.data.MockData
import com.nicolasbernal.codebase.data.SessionState
import com.nicolasbernal.codebase.ui.components.CodeBlock
import com.nicolasbernal.codebase.ui.components.CodeProgressBar
import com.nicolasbernal.codebase.ui.components.PrimaryButton
import com.nicolasbernal.codebase.ui.theme.BorderGray
import com.nicolasbernal.codebase.ui.theme.BlueXp
import com.nicolasbernal.codebase.ui.theme.ErrorDark
import com.nicolasbernal.codebase.ui.theme.ErrorLight
import com.nicolasbernal.codebase.ui.theme.ErrorRed
import com.nicolasbernal.codebase.ui.theme.GreenDark
import com.nicolasbernal.codebase.ui.theme.GreenLight
import com.nicolasbernal.codebase.ui.theme.GreenPrimary

/**
 * Pantallas 6 (Ejercicio) y 6b (Error) en un solo composable:
 * - Si aciertas -> panel verde y sigues.
 * - Si fallas  -> panel rojo con la explicación, pierdes una vida y reintentas.
 */
@Composable
fun ExerciseScreen(onClose: () -> Unit, onFinished: () -> Unit) {
    val questions = remember { MockData.questions }
    var index by remember { mutableIntStateOf(0) }
    var selected by remember { mutableStateOf<Int?>(null) }
    var checked by remember { mutableStateOf(false) }
    var hearts by remember { mutableIntStateOf(SessionState.hearts) }
    var correctCount by remember { mutableIntStateOf(0) }
    var mistakes by remember { mutableIntStateOf(0) }

    val question = questions[index]
    val isCorrect = selected == question.correctIndex

    Column(Modifier.fillMaxSize()) {
        // Barra superior
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onClose) { Icon(Icons.Default.Close, "Cerrar") }
            CodeProgressBar(
                progress = index.toFloat() / questions.size,
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(12.dp))
            Icon(Icons.Default.Favorite, null, tint = ErrorRed, modifier = Modifier.size(20.dp))
            Text(" $hearts", fontWeight = FontWeight.ExtraBold, color = ErrorRed)
            Spacer(Modifier.width(12.dp))
        }

        // Pregunta + opciones
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(question.prompt, style = MaterialTheme.typography.titleLarge)
            CodeBlock(question.code)
            question.options.forEachIndexed { i, text ->
                AnswerOption(
                    letter = ('A' + i).toString(),
                    text = text,
                    selected = selected == i,
                    state = when {
                        !checked -> OptionState.Idle
                        i == question.correctIndex -> OptionState.Correct
                        selected == i -> OptionState.Wrong
                        else -> OptionState.Idle
                    },
                    onClick = { if (!checked) selected = i },
                )
            }
        }

        // Zona inferior: botón o panel de feedback
        Column(Modifier.padding(16.dp)) {
            if (!checked) {
                PrimaryButton(
                    "COMPROBAR",
                    enabled = selected != null,
                    onClick = {
                        checked = true
                        if (!isCorrect) {
                            hearts -= 1
                            mistakes += 1
                        }
                    },
                )
            } else {
                FeedbackPanel(
                    correct = isCorrect,
                    explanation = question.explanation,
                    onContinue = {
                        if (isCorrect) {
                            correctCount += 1
                            if (index == questions.lastIndex) {
                                SessionState.lastGainedXp = correctCount * 15
                                SessionState.xp += correctCount * 15
                                SessionState.hearts = hearts
                                SessionState.lastAccuracy =
                                    questions.size * 100 / (questions.size + mistakes)
                                onFinished()
                            } else {
                                index += 1
                                selected = null
                                checked = false
                            }
                        } else if (hearts <= 0) {
                            SessionState.hearts = 5 // demo: recarga vidas
                            onClose()
                        } else {
                            selected = null // reintentar la misma pregunta
                            checked = false
                        }
                    },
                )
            }
        }
    }
}

private enum class OptionState { Idle, Correct, Wrong }

@Composable
private fun AnswerOption(letter: String, text: String, selected: Boolean, state: OptionState, onClick: () -> Unit) {
    val border = when (state) {
        OptionState.Correct -> GreenPrimary
        OptionState.Wrong -> ErrorRed
        OptionState.Idle -> if (selected) BlueXp else BorderGray
    }
    val bg = when (state) {
        OptionState.Correct -> GreenLight.copy(alpha = 0.5f)
        OptionState.Wrong -> ErrorLight
        OptionState.Idle -> Color.White
    }
    val shape = RoundedCornerShape(14.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(bg)
            .border(2.dp, border, shape)
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier.size(28.dp).clip(CircleShape).background(BorderGray),
            contentAlignment = Alignment.Center,
        ) { Text(letter, fontWeight = FontWeight.Bold) }
        Spacer(Modifier.width(12.dp))
        Text(text, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun FeedbackPanel(correct: Boolean, explanation: String, onContinue: () -> Unit) {
    val bg = if (correct) GreenLight else ErrorLight
    val titleColor = if (correct) GreenDark else ErrorDark
    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(bg).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            if (correct) "¡Excelente trabajo!" else "¡Respuesta incorrecta!",
            fontWeight = FontWeight.ExtraBold,
            color = titleColor,
        )
        Text(explanation, color = titleColor)
        PrimaryButton(
            text = if (correct) "CONTINUAR" else "ENTENDIDO, CONTINUAR",
            onClick = onContinue,
            color = if (correct) GreenPrimary else ErrorDark,
        )
    }
}
