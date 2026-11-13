package com.nicolasbernal.codebase.ui.screens.teacher

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolasbernal.codebase.data.SessionState
import com.nicolasbernal.codebase.ui.components.AvatarCircle
import com.nicolasbernal.codebase.ui.components.CodeCard
import com.nicolasbernal.codebase.ui.components.PrimaryButton
import com.nicolasbernal.codebase.ui.components.SecondaryButton
import com.nicolasbernal.codebase.ui.components.SectionTitle
import com.nicolasbernal.codebase.ui.components.showToast
import com.nicolasbernal.codebase.ui.theme.TextSecondary

// código para que los alumnos se unan al aula
private const val INVITE_CODE = "KOTLIN-2025"

@Composable
// pestaña Mi cuenta
fun TeacherAccountScreen(onLogout: () -> Unit) {
    val context = LocalContext.current
    var studentView by remember { mutableStateOf(false) }
    val teacherName = SessionState.userName.ifBlank { "Carlos Mendoza" }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AvatarCircle("👨‍🏫", size = 64.dp)
            Column {
                Text("Prof. $teacherName", style = MaterialTheme.typography.titleLarge)
                Text("Docente Certificado", color = TextSecondary, fontSize = 13.sp)
            }
        }

        CodeCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Modo Vista de Alumno", fontWeight = FontWeight.Bold)
                    Text("Prueba retos tal como los ve el alumno", color = TextSecondary, fontSize = 13.sp)
                }
                Switch(
                    checked = studentView,
                    onCheckedChange = {
                        studentView = it
                        showToast(context, if (it) "Vista de alumno activada" else "Vista de profesor")
                    },
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CodeCard(Modifier.weight(1f)) { Text("2", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp); Text("Grupos", color = TextSecondary) }
            CodeCard(Modifier.weight(1f)) { Text("52", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp); Text("Estudiantes", color = TextSecondary) }
            CodeCard(Modifier.weight(1f)) { Text("94%", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp); Text("Retención", color = TextSecondary) }
        }

        SectionTitle("Acceso al Aula CS-101")
        CodeCard {
            Text("CÓDIGO DE INVITACIÓN", color = TextSecondary, fontSize = 11.sp)
            Text(INVITE_CODE, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
        }
        // copia el código al portapapeles
        PrimaryButton("COPIAR CÓDIGO", onClick = {
            val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.setPrimaryClip(ClipData.newPlainText("codigo", INVITE_CODE))
            showToast(context, "Código copiado: $INVITE_CODE")
        })
        // abre el menú de compartir de Android
        SecondaryButton("COMPARTIR CÓDIGO", onClick = {
            val send = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Únete a mi aula en CodeBase con el código $INVITE_CODE")
            }
            context.startActivity(Intent.createChooser(send, "Compartir código"))
        })

        SectionTitle("Configuración del aula")
        listOf("Criterios de evaluación", "Alertas y seguimiento", "Vincular LMS").forEach { item ->
            CodeCard(onClick = { showToast(context, "$item: próximamente") }) {
                Text(item, fontWeight = FontWeight.Bold)
            }
        }

        SecondaryButton("CERRAR SESIÓN", onClick = onLogout)
    }
}
