package com.nicolasbernal.codebase.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Estado en memoria para la demo. Más adelante esto se reemplaza por
 * un ViewModel + Room / Firebase.
 */
object SessionState {
    var role by mutableStateOf(UserRole.STUDENT)
    var onboardingDone by mutableStateOf(false)   // si ya hizo onboarding, el login lo salta
    var hearts by mutableIntStateOf(5)
    var xp by mutableIntStateOf(465)
    var streak by mutableIntStateOf(8)
    var lastGainedXp by mutableIntStateOf(0)
    var lastAccuracy by mutableIntStateOf(100)
}
