package com.nicolasbernal.codebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nicolasbernal.codebase.navigation.AppNavGraph
import com.nicolasbernal.codebase.ui.theme.CodeBaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeBaseTheme {
                AppNavGraph()
            }
        }
    }
}
