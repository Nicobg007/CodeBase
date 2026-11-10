package com.nicolasbernal.codebase.navigation

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val ONBOARDING = "onboarding"
    const val PATH = "path"
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

    fun lesson(id: Int) = "lesson/$id"
    fun exercise(id: Int) = "exercise/$id"

    val studentTabRoutes = setOf(PATH, ACHIEVEMENTS, PROFILE)
    val teacherTabRoutes = setOf(TEACHER_GROUPS, TEACHER_TASKS, TEACHER_ACCOUNT)
}
