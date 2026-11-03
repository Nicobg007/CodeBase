package com.nicolasbernal.codebase.ui.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolasbernal.codebase.data.LessonState
import com.nicolasbernal.codebase.data.MockData
import com.nicolasbernal.codebase.data.PathNode
import com.nicolasbernal.codebase.data.SessionState
import com.nicolasbernal.codebase.ui.components.CodeProgressBar
import com.nicolasbernal.codebase.ui.components.Pill
import com.nicolasbernal.codebase.ui.components.TopStatsBar
import com.nicolasbernal.codebase.ui.theme.BorderGray
import com.nicolasbernal.codebase.ui.theme.GreenDark
import com.nicolasbernal.codebase.ui.theme.GreenPrimary
import com.nicolasbernal.codebase.ui.theme.PurpleAccent
import com.nicolasbernal.codebase.ui.theme.TextSecondary

@Composable
fun PathScreen(onOpenLesson: () -> Unit) {
    val zigzag = listOf(0, 48, 0, -48)

    Column(Modifier.fillMaxSize()) {
        TopStatsBar(SessionState.streak, SessionState.xp, SessionState.hearts)
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item { ModuleHeader() }
            itemsIndexed(MockData.pathNodes) { index, node ->
                PathNodeItem(node, Modifier.offset(x = zigzag[index % 4].dp), onOpenLesson)
            }
        }
    }
}

@Composable
private fun ModuleHeader() {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(PurpleAccent)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Pill("MÓDULO 1 · Fundamentos", Color.White.copy(alpha = 0.2f), Color.White)
        Text("Variables y Sintaxis", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
        Text("Estructura y lógica algorítmica", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
        CodeProgressBar(progress = 0.4f, color = Color.White)
    }
}

@Composable
private fun PathNodeItem(node: PathNode, modifier: Modifier, onClick: () -> Unit) {
    val locked = node.state == LessonState.LOCKED
    val circleColor = when (node.state) {
        LessonState.DONE -> GreenPrimary
        LessonState.CURRENT -> GreenPrimary
        LessonState.AVAILABLE -> PurpleAccent
        LessonState.LOCKED -> BorderGray
    }
    val size = if (node.state == LessonState.CURRENT) 88.dp else 72.dp

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (node.state == LessonState.CURRENT) {
            Pill("¡EMPIEZA AQUÍ!", GreenPrimary, Color.White, Modifier.padding(bottom = 6.dp))
        }
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
        Text(
            node.title,
            fontWeight = FontWeight.Bold,
            color = if (locked) TextSecondary else GreenDark,
            fontSize = 13.sp,
            modifier = Modifier.padding(top = 6.dp),
        )
    }
}
