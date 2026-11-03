package com.nicolasbernal.codebase.navigation

object Routes {
    // Estudiante
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val ONBOARDING = "onboarding"
    const val PATH = "path"
    const val LESSON = "lesson"
    const val EXERCISE = "exercise"
    const val COMPLETE = "complete"
    const val ACHIEVEMENTS = "achievements"
    const val PROFILE = "profile"

    // Profesor
    const val TEACHER_GROUPS = "teacher/groups"
    const val TEACHER_TASKS = "teacher/tasks"
    const val TEACHER_ACCOUNT = "teacher/account"
    const val CREATE_CHALLENGE = "teacher/create"
    const val SUBMISSIONS = "teacher/submissions"

    val studentTabRoutes = setOf(PATH, ACHIEVEMENTS, PROFILE)
    val teacherTabRoutes = setOf(TEACHER_GROUPS, TEACHER_TASKS, TEACHER_ACCOUNT)
}
