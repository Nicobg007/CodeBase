package com.nicolasbernal.codebase.data

enum class UserRole { STUDENT, TEACHER }
enum class LessonState { DONE, CURRENT, AVAILABLE, LOCKED }

data class PathNode(val title: String, val state: LessonState, val emoji: String)

data class Question(
    val prompt: String,
    val code: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
)

data class Achievement(val title: String, val description: String, val progress: Float, val done: Boolean)

data class ClassGroup(val name: String, val students: Int, val completion: Float)
data class StudentAlert(val name: String, val reason: String)
data class TeacherTask(val title: String, val subtitle: String, val delivered: Int, val total: Int, val badge: String)
data class Submission(val name: String, val detail: String, val status: String, val xp: Int?)
