package com.nicolasbernal.codebase.ui.components

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Handler
import android.os.Looper
import com.nicolasbernal.codebase.data.SessionState

// sonidos simples con ToneGenerator, no hace falta ningún archivo de audio
object SoundFx {
    private val handler = Handler(Looper.getMainLooper())

    private fun play(tone: Int, millis: Int) {
        // respeta el ajuste de sonido del perfil
        if (!SessionState.soundOn) return
        try {
            val generator = ToneGenerator(AudioManager.STREAM_MUSIC, 70)
            generator.startTone(tone, millis)
            handler.postDelayed({ generator.release() }, (millis + 150).toLong())
        } catch (e: RuntimeException) {
            // si el teléfono no puede reproducir el sonido, seguimos sin él
        }
    }

    // un sonido para acertar, otro para fallar y otro al terminar la lección
    fun correct() = play(ToneGenerator.TONE_PROP_ACK, 250)

    fun wrong() = play(ToneGenerator.TONE_PROP_NACK, 300)

    fun win() = play(ToneGenerator.TONE_PROP_PROMPT, 400)
}
