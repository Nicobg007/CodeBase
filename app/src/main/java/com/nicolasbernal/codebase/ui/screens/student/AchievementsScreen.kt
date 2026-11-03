package com.nicolasbernal.codebase.ui.screens.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nicolasbernal.codebase.data.MockData
import com.nicolasbernal.codebase.ui.components.CodeCard
import com.nicolasbernal.codebase.ui.components.CodeProgressBar
import com.nicolasbernal.codebase.ui.theme.TextSecondary

/** Pestaña "Logros" (no está detallada en el diseño; es una lista sencilla). */
@Composable
fun AchievementsScreen() {
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Logros e Insignias", style = MaterialTheme.typography.headlineMedium)
        MockData.achievements.forEach { a ->
            CodeCard {
                Text(a.title, fontWeight = FontWeight.Bold)
                Text(a.description, color = TextSecondary)
                CodeProgressBar(a.progress, modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}
