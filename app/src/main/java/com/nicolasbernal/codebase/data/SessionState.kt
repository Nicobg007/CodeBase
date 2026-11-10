package com.nicolasbernal.codebase.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.time.LocalDate

object SessionState {
    var role by mutableStateOf(UserRole.STUDENT)
    var loggedIn by mutableStateOf(false)
    var onboardingDone by mutableStateOf(false)
    var userName by mutableStateOf("")
    var dailyGoalMin by mutableIntStateOf(10)
    var dailyGoalOn by mutableStateOf(true)

    var hearts by mutableIntStateOf(5)
    var xp by mutableIntStateOf(0)
    var streak by mutableIntStateOf(0)
    var completedLessons by mutableIntStateOf(0)
    var totalCorrect by mutableIntStateOf(0)
    var totalAttempts by mutableIntStateOf(0)
    var perfectLessons by mutableIntStateOf(0)
    var lastStudyDay by mutableLongStateOf(0L)
    var lessonsToday by mutableIntStateOf(0)

    var lastGainedXp by mutableIntStateOf(0)
    var lastAccuracy by mutableIntStateOf(100)
    var lastLessonTitle by mutableStateOf("")

    private var prefs: SharedPreferences? = null

    val accuracy: Int get() = if (totalAttempts == 0) 100 else totalCorrect * 100 / totalAttempts
    val level: Int get() = xp / 100 + 1
    val league: String
        get() = when {
            level < 3 -> "LIGA BRONCE"
            level < 6 -> "LIGA PLATA"
            else -> "LIGA ORO"
        }

    fun init(context: Context) {
        val p = context.applicationContext.getSharedPreferences("codebase_prefs", Context.MODE_PRIVATE)
        prefs = p
        role = if (p.getString("role", "STUDENT") == "TEACHER") UserRole.TEACHER else UserRole.STUDENT
        loggedIn = p.getBoolean("loggedIn", false)
        onboardingDone = p.getBoolean("onboardingDone", false)
        userName = p.getString("userName", "") ?: ""
        dailyGoalMin = p.getInt("dailyGoalMin", 10)
        dailyGoalOn = p.getBoolean("dailyGoalOn", true)
        hearts = p.getInt("hearts", 5)
        xp = p.getInt("xp", 0)
        streak = p.getInt("streak", 0)
        completedLessons = p.getInt("completedLessons", 0)
        totalCorrect = p.getInt("totalCorrect", 0)
        totalAttempts = p.getInt("totalAttempts", 0)
        perfectLessons = p.getInt("perfectLessons", 0)
        lastStudyDay = p.getLong("lastStudyDay", 0L)
        lessonsToday = p.getInt("lessonsToday", 0)

        val today = LocalDate.now().toEpochDay()
        if (lastStudyDay != today) lessonsToday = 0
        if (lastStudyDay != 0L && today - lastStudyDay > 1L) streak = 0
    }

    fun save() {
        val e = prefs?.edit() ?: return
        e.putString("role", role.name)
        e.putBoolean("loggedIn", loggedIn)
        e.putBoolean("onboardingDone", onboardingDone)
        e.putString("userName", userName)
        e.putInt("dailyGoalMin", dailyGoalMin)
        e.putBoolean("dailyGoalOn", dailyGoalOn)
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

    fun finishLesson(lessonIndex: Int, lessonTitle: String, gainedXp: Int, correct: Int, attempts: Int) {
        val today = LocalDate.now().toEpochDay()
        streak = when {
            lastStudyDay == today -> maxOf(streak, 1)
            lastStudyDay == today - 1L -> streak + 1
            else -> 1
        }
        if (lastStudyDay != today) lessonsToday = 0
        lessonsToday += 1
        lastStudyDay = today
        if (lessonIndex >= completedLessons) completedLessons = lessonIndex + 1
        xp += gainedXp
        totalCorrect += correct
        totalAttempts += attempts
        if (correct == attempts) perfectLessons += 1
        lastGainedXp = gainedXp
        lastAccuracy = if (attempts == 0) 100 else correct * 100 / attempts
        lastLessonTitle = lessonTitle
        save()
    }

    fun logout() {
        loggedIn = false
        save()
    }

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
            a("Modo Relámpago", "Completa 3 lecciones en un día", "⚡", lessonsToday, 3),
            a("Sin fallos", "Termina una lección al 100%", "🎯", perfectLessons, 1),
            a("Estudiante estrella", "Consigue 200 XP", "⭐", xp, 200),
            a("Maestro de Kotlin", "Completa todas las lecciones", "🏆", completedLessons, MockData.lessons.size),
        )
    }
}
