package com.nicolasbernal.codebase.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.time.LocalDate

// guarda el estado de la app y lo persiste con SharedPreferences
object SessionState {
    // datos de la sesión
    var role by mutableStateOf(UserRole.STUDENT)
    var loggedIn by mutableStateOf(false)
    var onboardingDone by mutableStateOf(false)
    var userName by mutableStateOf("")
    var dailyGoalMin by mutableIntStateOf(10)
    var dailyGoalOn by mutableStateOf(true)
    var soundOn by mutableStateOf(true)

    // progreso del alumno
    var hearts by mutableIntStateOf(5)
    var xp by mutableIntStateOf(0)
    var streak by mutableIntStateOf(0)
    var completedLessons by mutableIntStateOf(0)
    var totalCorrect by mutableIntStateOf(0)
    var totalAttempts by mutableIntStateOf(0)
    var perfectLessons by mutableIntStateOf(0)
    var lastStudyDay by mutableLongStateOf(0L)
    var lessonsToday by mutableIntStateOf(0)

    // resultado de la última lección, para la pantalla final
    var lastGainedXp by mutableIntStateOf(0)
    var lastAccuracy by mutableIntStateOf(100)
    var lastLessonTitle by mutableStateOf("")

    // donde se guardan los datos en el teléfono
    private var prefs: SharedPreferences? = null

    // porcentaje de aciertos total
    val accuracy: Int get() = if (totalAttempts == 0) 100 else totalCorrect * 100 / totalAttempts
    // cada 100 XP subimos de nivel
    val level: Int get() = xp / 100 + 1
    // cuántas lecciones equivalen a la meta diaria
    val dailyGoalLessons: Int
        get() = when (dailyGoalMin) {
            5 -> 1
            10 -> 2
            else -> 3
        }
    // si ya pasó el día, las lecciones de hoy vuelven a cero
    val todayLessons: Int
        get() = if (lastStudyDay == LocalDate.now().toEpochDay()) lessonsToday else 0
    val league: String
        get() = when {
            level < 3 -> "LIGA BRONCE"
            level < 6 -> "LIGA PLATA"
            else -> "LIGA ORO"
        }

    // lee los datos guardados cuando se abre la app
    fun init(context: Context) {
        val p = context.applicationContext.getSharedPreferences("codebase_prefs", Context.MODE_PRIVATE)
        prefs = p
        role = if (p.getString("role", "STUDENT") == "TEACHER") UserRole.TEACHER else UserRole.STUDENT
        loggedIn = p.getBoolean("loggedIn", false)
        onboardingDone = p.getBoolean("onboardingDone", false)
        userName = p.getString("userName", "") ?: ""
        dailyGoalMin = p.getInt("dailyGoalMin", 10)
        dailyGoalOn = p.getBoolean("dailyGoalOn", true)
        soundOn = p.getBoolean("soundOn", true)
        hearts = p.getInt("hearts", 5)
        xp = p.getInt("xp", 0)
        streak = p.getInt("streak", 0)
        completedLessons = p.getInt("completedLessons", 0)
        totalCorrect = p.getInt("totalCorrect", 0)
        totalAttempts = p.getInt("totalAttempts", 0)
        perfectLessons = p.getInt("perfectLessons", 0)
        lastStudyDay = p.getLong("lastStudyDay", 0L)
        lessonsToday = p.getInt("lessonsToday", 0)

        // revisamos si cambió el día para la racha
        val today = LocalDate.now().toEpochDay()
        if (lastStudyDay != today) lessonsToday = 0
        if (lastStudyDay != 0L && today - lastStudyDay > 1L) streak = 0
    }

    // escribe todo en SharedPreferences
    fun save() {
        val e = prefs?.edit() ?: return
        e.putString("role", role.name)
        e.putBoolean("loggedIn", loggedIn)
        e.putBoolean("onboardingDone", onboardingDone)
        e.putString("userName", userName)
        e.putInt("dailyGoalMin", dailyGoalMin)
        e.putBoolean("dailyGoalOn", dailyGoalOn)
        e.putBoolean("soundOn", soundOn)
        e.putInt("hearts", hearts)
        e.putInt("xp", xp)
        e.putInt("streak", streak)
        e.putInt("completedLessons", completedLessons)
        e.putInt("totalCorrect", totalCorrect)
        e.putInt("totalAttempts", totalAttempts)
        e.putInt("perfectLessons", perfectLessons)
        e.putLong("lastStudyDay", lastStudyDay)
        e.putInt("lessonsToday", lessonsToday)
        e.apply()
    }

    // se llama cuando el alumno termina una lección o un reto
    fun finishLesson(lessonIndex: Int, lessonTitle: String, gainedXp: Int, correct: Int, attempts: Int) {
        val today = LocalDate.now().toEpochDay()
        // racha: si estudió ayer sube, si faltó un día se reinicia
        streak = when {
            lastStudyDay == today -> maxOf(streak, 1)
            lastStudyDay == today - 1L -> streak + 1
            else -> 1
        }
        if (lastStudyDay != today) lessonsToday = 0
        lessonsToday += 1
        lastStudyDay = today
        // -1 es un reto del profe, ese no desbloquea lecciones
        if (lessonIndex >= 0 && lessonIndex >= completedLessons) completedLessons = lessonIndex + 1
        xp += gainedXp
        totalCorrect += correct
        totalAttempts += attempts
        if (correct == attempts) perfectLessons += 1
        lastGainedXp = gainedXp
        lastAccuracy = if (attempts == 0) 100 else correct * 100 / attempts
        lastLessonTitle = lessonTitle
        save()
    }

    // cerrar sesión no borra el progreso
    fun logout() {
        loggedIn = false
        save()
    }

    // deja todo en cero, sirve para repetir la demo
    fun resetProgress() {
        hearts = 5
        xp = 0
        streak = 0
        completedLessons = 0
        totalCorrect = 0
        totalAttempts = 0
        perfectLessons = 0
        lastStudyDay = 0L
        lessonsToday = 0
        save()
    }

    // los logros se calculan con el progreso actual
    fun achievements(): List<Achievement> {
        fun a(title: String, desc: String, emoji: String, value: Int, goal: Int) = Achievement(
            title = title,
            description = desc,
            progress = (value.toFloat() / goal).coerceIn(0f, 1f),
            done = value >= goal,
            emoji = emoji,
        )
        return listOf(
            a("Primer val en Kotlin", "Completa tu primera lección", "🌱", completedLessons, 1),
            a("Racha de Fuego", "Mantén 7 días de racha", "🔥", streak, 7),
            a("Modo Relámpago", "Completa 3 lecciones en un día", "⚡", todayLessons, 3),
            a("Sin fallos", "Termina una lección al 100%", "🎯", perfectLessons, 1),
            a("Estudiante estrella", "Consigue 200 XP", "⭐", xp, 200),
            a("Maestro de Kotlin", "Completa todas las lecciones", "🏆", completedLessons, MockData.lessons.size),
        )
    }
}
