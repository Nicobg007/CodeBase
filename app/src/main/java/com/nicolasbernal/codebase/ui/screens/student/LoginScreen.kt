package com.nicolasbernal.codebase.ui.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.nicolasbernal.codebase.data.UserRole
import com.nicolasbernal.codebase.ui.components.PrimaryButton
import com.nicolasbernal.codebase.ui.components.SecondaryButton
import com.nicolasbernal.codebase.ui.theme.BorderGray
import com.nicolasbernal.codebase.ui.theme.GreenLight
import com.nicolasbernal.codebase.ui.theme.GreenPrimary
import com.nicolasbernal.codebase.ui.theme.TextSecondary

@Composable
fun LoginScreen(onLogin: (UserRole) -> Unit) {
    var role by remember { mutableStateOf(UserRole.STUDENT) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Inicia sesión", style = MaterialTheme.typography.headlineMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            RoleChip("Soy Estudiante", role == UserRole.STUDENT, { role = UserRole.STUDENT }, Modifier.weight(1f))
            RoleChip("Soy Profesor", role == UserRole.TEACHER, { role = UserRole.TEACHER }, Modifier.weight(1f))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo o usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility, null)
                }
            },
            modifier = Modifier.fillMaxWidth(),
        )

        // Para la demo cualquier dato entra. Luego se conecta con Firebase Auth.
        PrimaryButton("INGRESAR", onClick = { onLogin(role) })

        Text("o", color = TextSecondary, modifier = Modifier.align(Alignment.CenterHorizontally))
        SecondaryButton("Continuar con Google", onClick = { /* TODO */ })
        SecondaryButton("Continuar con GitHub", onClick = { /* TODO */ })
    }
}

@Composable
private fun RoleChip(text: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) GreenLight else Color.White)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text,
            fontWeight = FontWeight.Bold,
            color = if (selected) GreenPrimary else TextSecondary,
        )
    }
}
