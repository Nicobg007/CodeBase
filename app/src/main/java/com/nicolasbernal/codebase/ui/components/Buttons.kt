package com.nicolasbernal.codebase.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nicolasbernal.codebase.ui.theme.BorderGray
import com.nicolasbernal.codebase.ui.theme.GreenPrimary
import com.nicolasbernal.codebase.ui.theme.TextPrimary

/** Botón verde grande: "CONTINUAR", "INGRESAR", "ENTENDIDO"... */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = GreenPrimary,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.White,
            disabledContainerColor = BorderGray,
            disabledContentColor = Color.Gray,
        ),
    ) {
        Text(text, fontWeight = FontWeight.ExtraBold)
    }
}

/** Botón blanco con borde: "YA TENGO UNA CUENTA", "Continuar con Google"... */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, BorderGray),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
    ) {
        Text(text, fontWeight = FontWeight.Bold)
    }
}
