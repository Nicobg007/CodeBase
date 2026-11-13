package com.nicolasbernal.codebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nicolasbernal.codebase.data.SessionState
import com.nicolasbernal.codebase.navigation.AppNavGraph
import com.nicolasbernal.codebase.ui.theme.CodeBaseTheme

// actividad principal, aquí arranca toda la app
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // cargamos el progreso guardado antes de dibujar la pantalla
        SessionState.init(applicationContext)
        // para que la app use toda la pantalla
        enableEdgeToEdge()
        // aquí empieza Compose: el tema y la navegación
        setContent {
            CodeBaseTheme {
                AppNavGraph()
            }
        }
    }
}
