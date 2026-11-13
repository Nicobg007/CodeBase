package com.nicolasbernal.codebase.ui.screens.student

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolasbernal.codebase.data.CourseModule
import com.nicolasbernal.codebase.data.LessonState
import com.nicolasbernal.codebase.data.MockData
import com.nicolasbernal.codebase.data.PathNode
import com.nicolasbernal.codebase.data.SessionState
import com.nicolasbernal.codebase.data.StudentChallenge
import com.nicolasbernal.codebase.data.TeacherState
import com.nicolasbernal.codebase.ui.components.ByteMascot
import com.nicolasbernal.codebase.ui.components.CodeCard
import com.nicolasbernal.codebase.ui.components.CodeProgressBar
import com.nicolasbernal.codebase.ui.components.Pill
import com.nicolasbernal.codebase.ui.components.PrimaryButton
import com.nicolasbernal.codebase.ui.components.TopStatsBar
import com.nicolasbernal.codebase.ui.theme.BorderGray
import com.nicolasbernal.codebase.ui.theme.GreenDark
import com.nicolasbernal.codebase.ui.theme.GreenLight
import com.nicolasbernal.codebase.ui.theme.GreenPrimary
import com.nicolasbernal.codebase.ui.theme.PurpleAccent
import com.nicolasbernal.codebase.ui.theme.PurpleLight
import com.nicolasbernal.codebase.ui.theme.TextSecondary

@Composable
// pantalla 4: ruta de aprendizaje, el home del alumno
fun PathScreen(onOpenLesson: (Int) -> Unit, onOpenChallenge: () -> Unit) {
    // desplazamientos para que los nodos vayan en zigzag
    val zigzag = listOf(0, 48, 0, -48)
    val lessons = MockData.lessons
    val done = SessionState.completedLessons
    // reto del profesor, si hay uno publicado
    val challenge = TeacherState.activeChallenge

    Column(Modifier.fillMaxSize()) {
        TopStatsBar(SessionState.streak, SessionState.xp, SessionState.hearts)
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            // tarjeta de meta diaria (se puede apagar en el perfil)
            if (SessionState.dailyGoalOn) {
                item(key = "goal") { DailyGoalCard() }
            }
            if (challenge != null && !challenge.done) {
                item(key = "challenge") { ChallengeCard(challenge, onOpenChallenge) }
            }
            // recorremos los módulos y dibujamos su encabezado y sus lecciones
            MockData.modules.forEachIndexed { m, module ->
                val entries = lessons.withIndex().filter { it.value.module == m }
                val doneInModule = entries.count { it.index < done }
                item(key = "header$m") { ModuleHeader(module, doneInModule, entries.size, m == 0) }
                items(entries, key = { it.index }) { entry ->
                    // las anteriores a la actual están hechas, la siguiente es la actual y el resto bloqueadas
                    val state = when {
                        entry.index < done -> LessonState.DONE
                        entry.index == done -> LessonState.CURRENT
                        else -> LessonState.LOCKED
                    }
                    PathNodeItem(
                        node = PathNode(entry.index, "${entry.index + 1}. ${entry.value.title}", state, entry.value.emoji),
                        modifier = Modifier.offset(x = zigzag[entry.index % 4].dp),
                        onClick = { onOpenLesson(entry.index) },
                    )
                }
            }
        }
    }
}

@Composable
// muestra cuántas lecciones llevas hoy frente a la meta
private fun DailyGoalCard() {
    val goal = SessionState.dailyGoalLessons
    val today = minOf(SessionState.todayLessons, goal)
    val reached = today >= goal
    CodeCard(
        borderColor = if (reached) GreenPrimary else BorderGray,
        containerColor = if (reached) GreenLight.copy(alpha = 0.4f) else Color.White,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(if (reached) "🏅" else "🎯", fontSize = 26.sp)
            Column(Modifier.weight(1f)) {
                Text(
                    if (reached) "¡Meta de hoy cumplida!" else "Meta de hoy: ${SessionState.dailyGoalMin} min",
                    fontWeight = FontWeight.Bold,
                )
                Text("$today de $goal lecciones", color = TextSecondary, fontSize = 13.sp)
            }
        }
        CodeProgressBar(today.toFloat() / goal, modifier = Modifier.padding(top = 10.dp))
    }
}

@Composable
// tarjeta morada con el reto que mandó el profe
private fun ChallengeCard(challenge: StudentChallenge, onOpen: () -> Unit) {
    CodeCard(borderColor = PurpleAccent, containerColor = PurpleLight) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("🎯", fontSize = 26.sp)
            Column(Modifier.weight(1f)) {
                Text("Reto del profe", fontWeight = FontWeight.ExtraBold, color = PurpleAccent)
                Text(challenge.title, fontWeight = FontWeight.Bold)
                Text("+${challenge.points} XP", color = TextSecondary, fontSize = 13.sp)
            }
        }
        PrimaryButton("ACEPTAR RETO", onClick = onOpen, color = PurpleAccent, modifier = Modifier.padding(top = 10.dp))
    }
}

@Composable
// encabezado morado de cada módulo con su progreso
private fun ModuleHeader(module: CourseModule, done: Int, total: Int, showByte: Boolean) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(PurpleAccent)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Pill(module.tag, Color.White.copy(alpha = 0.2f), Color.White)
            Text(module.title, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
            Text(
                if (done >= total) "¡Módulo completado! 🎉" else "$done de $total lecciones completadas",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 13.sp,
            )
            CodeProgressBar(progress = done.toFloat() / total, color = Color.White)
        }
        if (showByte) ByteMascot(mascotSize = 72.dp) else Text(module.emoji, fontSize = 44.sp)
    }
}

@Composable
// círculo de una lección: verde si está hecha o actual, gris si está bloqueada
private fun PathNodeItem(node: PathNode, modifier: Modifier, onClick: () -> Unit) {
    val locked = node.state == LessonState.LOCKED
    val current = node.state == LessonState.CURRENT
    val circleColor = when (node.state) {
        LessonState.DONE -> GreenPrimary
        LessonState.CURRENT -> GreenPrimary
        LessonState.AVAILABLE -> PurpleAccent
        LessonState.LOCKED -> BorderGray
    }
    val edgeColor = if (locked) Color(0xFFCBD5E1) else lerp(circleColor, Color.Black, 0.22f)
    val size = if (current) 88.dp else 72.dp

    // el nodo actual late para llamar la atención
    val pulse by rememberInfiniteTransition(label = "pulse").animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(tween(850), RepeatMode.Reverse),
        label = "pulseScale",
    )

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (current) {
            Pill("¡EMPIEZA AQUÍ!", GreenPrimary, Color.White, Modifier.padding(bottom = 6.dp))
        }
        Box(Modifier.scale(if (current) pulse else 1f)) {
            Box(Modifier.offset(y = 6.dp).size(size).clip(CircleShape).background(edgeColor))
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .background(circleColor)
                    .then(if (!locked) Modifier.clickable(onClick = onClick) else Modifier),
                contentAlignment = Alignment.Center,
            ) {
                when (node.state) {
                    LessonState.DONE -> Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(36.dp))
                    LessonState.LOCKED -> Icon(Icons.Default.Lock, null, tint = TextSecondary)
                    else -> Text(node.emoji, fontSize = 30.sp)
                }
            }
        }
        Text(
            node.title,
            fontWeight = FontWeight.Bold,
            color = if (locked) TextSecondary else GreenDark,
            fontSize = 13.sp,
            modifier = Modifier.padding(top = 12.dp),
        )
    }
}
