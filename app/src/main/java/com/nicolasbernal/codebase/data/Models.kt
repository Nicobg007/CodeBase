package com.nicolasbernal.codebase.data

enum class UserRole { STUDENT, TEACHER }
enum class LessonState { DONE, CURRENT, AVAILABLE, LOCKED }
enum class QuestionType { CHOICE, ORDER }

data class PathNode(val index: Int, val title: String, val state: LessonState, val emoji: String)

/**
 * CHOICE: options = respuestas, correctIndex = la correcta.
 * ORDER : options = las líneas de código YA en el orden correcto (la app las revuelve).
 */
data class Question(
    val type: QuestionType = QuestionType.CHOICE,
    val prompt: String,
    val code: String = "",
    val options: List<String>,
    val correctIndex: Int = 0,
    val explanation: String,
)

data class Lesson(
    val title: String,
    val emoji: String,
    val headline: String,
    val explanation: String,
    val example: String,
    val tip: String,
    val questions: List<Question>,
)

data class Achievement(
    val title: String,
    val description: String,
    val progress: Float,
    val done: Boolean,
    val emoji: String = "🏅",
)

data class ClassGroup(val name: String, val students: Int, val completion: Float)
data class StudentAlert(val name: String, val reason: String)
data class TeacherTask(val title: String, val subtitle: String, val delivered: Int, val total: Int, val badge: String)
data class Submission(val name: String, val detail: String, val status: String, val xp: Int?)
