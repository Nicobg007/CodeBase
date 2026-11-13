package com.nicolasbernal.codebase.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// estado del profesor en memoria (se pierde al cerrar la app)
object TeacherState {
    val tasks = mutableStateListOf<TeacherTask>().apply { addAll(MockData.tasks) }
    // entregas reales que hacen los alumnos desde la app
    val liveSubmissions = mutableStateListOf<Submission>()
    var selectedTask by mutableStateOf("")
    // último reto publicado, es el que ve el alumno
    var activeChallenge by mutableStateOf<StudentChallenge?>(null)

    // agrega el reto a la lista y se lo manda al alumno
    fun publish(task: TeacherTask, challenge: StudentChallenge?) {
        tasks.add(0, task)
        if (challenge != null) {
            activeChallenge = challenge
            liveSubmissions.clear()
        }
    }

    // cuando el alumno termina el reto sumamos una entrega
    fun completeChallenge(studentName: String, attempts: Int) {
        val c = activeChallenge ?: return
        activeChallenge = c.copy(done = true)
        val i = tasks.indexOfFirst { it.title == c.title }
        if (i >= 0) {
            val t = tasks[i]
            tasks[i] = t.copy(delivered = (t.delivered + 1).coerceAtMost(t.total))
        }
        val status = if (attempts <= 1) "Aprobado (1er intento)" else "Aprobado ($attempts intentos)"
        liveSubmissions.add(0, Submission(studentName, "Ahora · 100% test passing", status, c.points))
    }
}
