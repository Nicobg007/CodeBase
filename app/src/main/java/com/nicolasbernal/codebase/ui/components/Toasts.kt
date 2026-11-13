package com.nicolasbernal.codebase.ui.components

import android.content.Context
import android.widget.Toast

// muestra un mensajito corto en pantalla
fun showToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}
