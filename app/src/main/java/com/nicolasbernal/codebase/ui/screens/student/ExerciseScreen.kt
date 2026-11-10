package com.nicolasbernal.codebase.ui.screens.student

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolasbernal.codebase.data.MockData
import com.nicolasbernal.codebase.data.QuestionType
import com.nicolasbernal.codebase.data.SessionState
import com.nicolasbernal.codebase.ui.components.ByteMascot
import com.nicolasbernal.codebase.ui.components.ByteMood
import com.nicolasbernal.codebase.ui.components.CodeBlock
import com.nicolasbernal.codebase.ui.components.CodeProgressBar
import com.nicolasbernal.codebase.ui.components.PrimaryButton
import com.nicolasbernal.codebase.ui.theme.BlueXp
import com.nicolasbernal.codebase.ui.theme.BorderGray
import com.nicolasbernal.codebase.ui.theme.CodeBackground
import com.nicolasbernal.codebase.ui.theme.ErrorDark
import com.nicolasbernal.codebase.ui.theme.ErrorLight
import com.nicolasbernal.codebase.ui.theme.ErrorRed
import com.nicolasbernal.codebase.ui.theme.GreenDark
import com.nicolasbernal.codebase.ui.theme.GreenLight
import com.nicolasbernal.codebase.ui.theme.GreenPrimary
import com.nicolasbernal.codebase.ui.theme.TextSecondary

/**
 * Ejercicio de una lección. Soporta dos tipos de pregunta:
 *  - CHOICE: opción múltiple
 *  - ORDER : ordenar líneas de código
 * Si fallas: panel rojo (pantalla 6b), pierdes una vida y reintentas.
 */
@Composable
fun ExerciseScreen(lessonIndex: Int, onClose: () -> Unit, onFinished: () -> Unit) {
    val lesson = remember(lessonIndex) { MockData.lessons[lessonIndex.coerceIn(0, MockData.lessons.lastIndex)] }
    val questions = lesson.questions

    var index by remember { mutableIntStateOf(0) }
    var selected by remember { mutableStateOf<Int?>(null) }
    val picked = remember { mutableStateListOf<Int>() }
    var checked by remember { mutableStateOf(false) }
    var hearts by remember { mutableIntStateOf(SessionState.hearts) }
    var correctCount by remember { mutableIntStateOf(0) }
    var mistakes by remember { mutableIntStateOf(0) }
    var feedbackCorrect by remember { mutableStateOf(true) }
    var feedbackText by remember { mutableStateOf("") }

    val haptic = LocalHapticFeedback.current
    val question = questions[index]
    val isOrder = question.type == QuestionType.ORDER
    val shuffled = remember(index) { shuffleDifferent(question.options) }

    val isCorrect = if (isOrder) {
        picked.size == question.options.size && picked.map { shuffled[it] } == question.options
    } else {
        selected == question.correctIndex
    }
    val canCheck = if (isOrder) picked.size == question.options.size else selected != null

    fun resetQuestion() {
        selected = null
        picked.clear()
        checked = false
    }

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
            if (question.code.isNotBlank()) CodeBlock(question.code)

            if (isOrder) {
                OrderArea(
                    shuffled = shuffled,
                    picked = picked,
                    checked = checked,
                    correct = isCorrect,
                    onPick = { picked.add(it) },
                    onUnpick = { picked.removeAt(it) },
                )
            } else {
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
        }

        // Zona inferior: botón o panel de feedback
        Column(Modifier.padding(16.dp)) {
            if (!checked) {
                PrimaryButton(
                    "COMPROBAR",
                    enabled = canCheck,
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        feedbackCorrect = isCorrect
                        feedbackText = question.explanation
                        checked = true
                        if (!isCorrect) {
                            hearts -= 1
                            mistakes += 1
                        }
                    },
                )
            }
            AnimatedVisibility(
                visible = checked,
                enter = slideInVertically(tween(300)) { it } + fadeIn(tween(300)),
                exit = fadeOut(tween(100)),
            ) {
                FeedbackPanel(
                    correct = feedbackCorrect,
                    explanation = feedbackText,
                    onContinue = {
                        if (feedbackCorrect) {
                            correctCount += 1
                            if (index == questions.lastIndex) {
                                SessionState.hearts = hearts
                                SessionState.finishLesson(
                                    lessonIndex = lessonIndex,
                                    lessonTitle = lesson.title,
                                    gainedXp = questions.size * 15,
                                    correct = questions.size,
                                    attempts = questions.size + mistakes,
                                )
                                onFinished()
                            } else {
                                index += 1
                                resetQuestion()
                            }
                        } else if (hearts <= 0) {
                            SessionState.hearts = 5 // demo: se recargan las vidas
                            SessionState.save()
                            onClose()
                        } else {
                            resetQuestion() // reintentar la misma pregunta
                        }
                    },
                )
            }
        }
    }
}

private fun shuffleDifferent(lines: List<String>): List<String> {
    if (lines.size < 2) return lines
    var s = lines.shuffled()
    var tries = 0
    while (s == lines && tries < 10) {
        s = lines.shuffled()
        tries++
    }
    return s
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

/** Pregunta tipo "ordena el código": toca las líneas del banco para armar tu respuesta. */
@Composable
private fun OrderArea(
    shuffled: List<String>,
    picked: List<Int>,
    checked: Boolean,
    correct: Boolean,
    onPick: (Int) -> Unit,
    onUnpick: (Int) -> Unit,
) {
    val borderColor = when {
        !checked -> BorderGray
        correct -> GreenPrimary
        else -> ErrorRed
    }
    val shape = RoundedCornerShape(16.dp)
    val lineShape = RoundedCornerShape(10.dp)

    Text("Toca las líneas en el orden correcto (toca una línea de tu respuesta para quitarla)", color = TextSecondary, fontSize = 13.sp)

    Column(
        Modifier
            .fillMaxWidth()
            .heightIn(min = 120.dp)
            .clip(shape)
            .background(CodeBackground)
            .border(2.dp, borderColor, shape)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        if (picked.isEmpty()) {
            Text("Tu código aparecerá aquí…", color = Color(0xFF94A3B8), fontFamily = FontFamily.Monospace, fontSize = 14.sp)
        }
        picked.forEachIndexed { pos, idx ->
            Text(
                shuffled[idx],
                color = Color(0xFFE2E8F0),
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(lineShape)
                    .background(Color(0x22FFFFFF))
                    .clickable(enabled = !checked) { onUnpick(pos) }
                    .padding(8.dp),
            )
        }
    }

    shuffled.forEachIndexed { idx, line ->
        val used = idx in picked
        Text(
            line,
            fontFamily = FontFamily.Monospace,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (used) 0.25f else 1f)
                .clip(lineShape)
                .background(Color.White)
                .border(1.5.dp, BorderGray, lineShape)
                .clickable(enabled = !used && !checked) { onPick(idx) }
                .padding(12.dp),
        )
    }
}

@Composable
private fun FeedbackPanel(correct: Boolean, explanation: String, onContinue: () -> Unit) {
    val bg = if (correct) GreenLight else ErrorLight
    val titleColor = if (correct) GreenDark else ErrorDark
    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(bg).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ByteMascot(mascotSize = 56.dp, mood = if (correct) ByteMood.Happy else ByteMood.Sad)
            Column(Modifier.weight(1f)) {
                Text(
                    if (correct) "¡Excelente trabajo!" else "¡Respuesta incorrecta!",
                    fontWeight = FontWeight.ExtraBold,
                    color = titleColor,
                )
                Text(explanation, color = titleColor)
            }
        }
        PrimaryButton(
            text = if (correct) "CONTINUAR" else "ENTENDIDO, CONTINUAR",
            onClick = onContinue,
            color = if (correct) GreenPrimary else ErrorDark,
        )
    }
}
