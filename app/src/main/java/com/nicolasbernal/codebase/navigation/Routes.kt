package com.nicolasbernal.codebase.navigation

// nombres de todas las pantallas
object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val ONBOARDING = "onboarding"
    const val PATH = "path"
    // estas dos llevan el número de la lección en la ruta
    const val LESSON = "lesson/{lessonId}"
    const val EXERCISE = "exercise/{lessonId}"
    const val COMPLETE = "complete"
    const val ACHIEVEMENTS = "achievements"
    const val PROFILE = "profile"

    const val TEACHER_GROUPS = "teacher/groups"
    const val TEACHER_TASKS = "teacher/tasks"
    const val TEACHER_ACCOUNT = "teacher/account"
    const val CREATE_CHALLENGE = "teacher/create"
    const val SUBMISSIONS = "teacher/submissions"

    // arma la ruta con el número, por ejemplo lesson/2
    fun lesson(id: Int) = "lesson/$id"
    fun exercise(id: Int) = "exercise/$id"

    // pantallas donde se muestra la barra de abajo
    val studentTabRoutes = setOf(PATH, ACHIEVEMENTS, PROFILE)
    val teacherTabRoutes = setOf(TEACHER_GROUPS, TEACHER_TASKS, TEACHER_ACCOUNT)
}
