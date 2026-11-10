package com.nicolasbernal.codebase.ui.screens.student

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nicolasbernal.codebase.ui.components.ByteMascot
import com.nicolasbernal.codebase.ui.components.PrimaryButton
import com.nicolasbernal.codebase.ui.components.SecondaryButton
import com.nicolasbernal.codebase.ui.theme.GreenDark
import com.nicolasbernal.codebase.ui.theme.TextSecondary

@Composable
fun SplashScreen(onStart: () -> Unit, onHaveAccount: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(700)) + scaleIn(tween(700), initialScale = 0.7f),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "¡Aprender a programar es divertido y gratis! ✨",
                    textAlign = TextAlign.Center,
                    color = TextSecondary,
                )
                Spacer(Modifier.height(24.dp))
                ByteMascot(mascotSize = 160.dp)
                Spacer(Modifier.height(16.dp))
                Text("CodeBase", style = MaterialTheme.typography.headlineMedium, color = GreenDark)
                Text(
                    "Domina las bases de la programación jugando solo 10 minutos al día.",
                    textAlign = TextAlign.Center,
                    color = TextSecondary,
                    modifier = Modifier.padding(vertical = 12.dp),
                )
            }
        }
        Spacer(Modifier.height(24.dp))
        PrimaryButton("¡EMPIEZA AHORA!", onStart)
        Spacer(Modifier.height(12.dp))
        SecondaryButton("YA TENGO UNA CUENTA", onHaveAccount)
    }
}
