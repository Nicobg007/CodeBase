package com.nicolasbernal.codebase.data

// roles y estados que usamos en varias pantallas
enum class UserRole { STUDENT, TEACHER }
enum class LessonState { DONE, CURRENT, AVAILABLE, LOCKED }
enum class QuestionType { CHOICE, ORDER, FILL }

// un nodo de la ruta de aprendizaje (cada lección)
data class PathNode(val index: Int, val title: String, val state: LessonState, val emoji: String)

// una pregunta: CHOICE es opción múltiple, ORDER ordenar código y FILL escribir la respuesta
data class Question(
    val type: QuestionType = QuestionType.CHOICE,
    val prompt: String,
    val code: String = "",
    val options: List<String>,
    val correctIndex: Int = 0,
    val explanation: String,
)

// una lección con su teoría, ejemplo y preguntas
data class Lesson(
    val title: String,
    val emoji: String,
    val headline: String,
    val explanation: String,
    val example: String,
    val tip: String,
    val questions: List<Question>,
    val module: Int = 0,
)

// un módulo agrupa varias lecciones
data class CourseModule(val title: String, val tag: String, val emoji: String)

// reto que publica el profesor y le llega al alumno
data class StudentChallenge(
    val title: String,
    val points: Int,
    val question: Question,
    val done: Boolean = false,
)

data class RankEntry(val name: String, val xp: Int)

// logro o insignia del perfil
data class Achievement(
    val title: String,
    val description: String,
    val progress: Float,
    val done: Boolean,
    val emoji: String = "🏅",
)

// datos del lado del profesor
data class ClassGroup(val name: String, val students: Int, val completion: Float)
data class StudentAlert(val name: String, val reason: String)
data class TeacherTask(val title: String, val subtitle: String, val delivered: Int, val total: Int, val badge: String)
data class Submission(val name: String, val detail: String, val status: String, val xp: Int?)
