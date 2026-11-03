package com.nicolasbernal.codebase.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nicolasbernal.codebase.data.SessionState
import com.nicolasbernal.codebase.data.UserRole
import com.nicolasbernal.codebase.ui.components.BottomTab
import com.nicolasbernal.codebase.ui.components.CodeBottomBar
import com.nicolasbernal.codebase.ui.screens.student.AchievementsScreen
import com.nicolasbernal.codebase.ui.screens.student.ExerciseScreen
import com.nicolasbernal.codebase.ui.screens.student.LessonCompleteScreen
import com.nicolasbernal.codebase.ui.screens.student.LessonScreen
import com.nicolasbernal.codebase.ui.screens.student.LoginScreen
import com.nicolasbernal.codebase.ui.screens.student.OnboardingScreen
import com.nicolasbernal.codebase.ui.screens.student.PathScreen
import com.nicolasbernal.codebase.ui.screens.student.ProfileScreen
import com.nicolasbernal.codebase.ui.screens.student.SplashScreen
import com.nicolasbernal.codebase.ui.screens.teacher.CreateChallengeScreen
import com.nicolasbernal.codebase.ui.screens.teacher.SubmissionsScreen
import com.nicolasbernal.codebase.ui.screens.teacher.TeacherAccountScreen
import com.nicolasbernal.codebase.ui.screens.teacher.TeacherGroupsScreen
import com.nicolasbernal.codebase.ui.screens.teacher.TeacherTasksScreen

private val studentTabs = listOf(
    BottomTab(Routes.PATH, "Aprender", Icons.Default.Home),
    BottomTab(Routes.ACHIEVEMENTS, "Logros", Icons.Default.EmojiEvents),
    BottomTab(Routes.PROFILE, "Perfil", Icons.Default.Person),
)

private val teacherTabs = listOf(
    BottomTab(Routes.TEACHER_GROUPS, "Grupos", Icons.Default.Groups),
    BottomTab(Routes.TEACHER_TASKS, "Tareas y retos", Icons.AutoMirrored.Filled.Assignment),
    BottomTab(Routes.TEACHER_ACCOUNT, "Mi cuenta", Icons.Default.Person),
)

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val tabs = when (currentRoute) {
        in Routes.studentTabRoutes -> studentTabs
        in Routes.teacherTabRoutes -> teacherTabs
        else -> null
    }

    fun goToTab(route: String) {
        val root = tabs?.first()?.route ?: return
        navController.navigate(route) {
            popUpTo(root) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (tabs != null) CodeBottomBar(tabs, currentRoute) { goToTab(it) }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.SPLASH,
            modifier = Modifier.padding(padding),
            enterTransition = { fadeIn(tween(250)) + slideInHorizontally(tween(250)) { it / 8 } },
            exitTransition = { fadeOut(tween(150)) },
            popEnterTransition = { fadeIn(tween(250)) },
            popExitTransition = { fadeOut(tween(150)) + slideOutHorizontally(tween(250)) { it / 8 } },
        ) {
            // ---------- ESTUDIANTE ----------
            composable(Routes.SPLASH) {
                SplashScreen(
                    onStart = { navController.navigate(Routes.LOGIN) },
                    onHaveAccount = { navController.navigate(Routes.LOGIN) },
                )
            }
            composable(Routes.LOGIN) {
                LoginScreen(onLogin = { role ->
                    SessionState.role = role
                    when {
                        role == UserRole.TEACHER ->
                            navController.navigate(Routes.TEACHER_GROUPS) {
                                popUpTo(Routes.SPLASH) { inclusive = true }
                            }
                        SessionState.onboardingDone -> // atajo: ya tiene cuenta
                            navController.navigate(Routes.PATH) {
                                popUpTo(Routes.SPLASH) { inclusive = true }
                            }
                        else -> navController.navigate(Routes.ONBOARDING)
                    }
                })
            }
            composable(Routes.ONBOARDING) {
                OnboardingScreen(onFinish = {
                    SessionState.onboardingDone = true
                    navController.navigate(Routes.PATH) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                })
            }
            composable(Routes.PATH) {
                PathScreen(onOpenLesson = { navController.navigate(Routes.LESSON) })
            }
            composable(Routes.LESSON) {
                LessonScreen(
                    onBack = { navController.popBackStack() },
                    onContinue = { navController.navigate(Routes.EXERCISE) },
                )
            }
            composable(Routes.EXERCISE) {
                ExerciseScreen(
                    onClose = { navController.popBackStack(Routes.PATH, inclusive = false) },
                    onFinished = { navController.navigate(Routes.COMPLETE) },
                )
            }
            composable(Routes.COMPLETE) {
                LessonCompleteScreen(onContinue = {
                    navController.popBackStack(Routes.PATH, inclusive = false)
                })
            }
            composable(Routes.ACHIEVEMENTS) { AchievementsScreen() }
            composable(Routes.PROFILE) {
                ProfileScreen(onLogout = {
                    navController.navigate(Routes.SPLASH) {
                        popUpTo(Routes.PATH) { inclusive = true }
                    }
                })
            }

            // ---------- PROFESOR ----------
            composable(Routes.TEACHER_GROUPS) {
                TeacherGroupsScreen(onLaunchChallenge = { navController.navigate(Routes.CREATE_CHALLENGE) })
            }
            composable(Routes.TEACHER_TASKS) {
                TeacherTasksScreen(
                    onNewTask = { navController.navigate(Routes.CREATE_CHALLENGE) },
                    onSeeSubmissions = { navController.navigate(Routes.SUBMISSIONS) },
                )
            }
            composable(Routes.TEACHER_ACCOUNT) {
                TeacherAccountScreen(onLogout = {
                    navController.navigate(Routes.SPLASH) {
                        popUpTo(Routes.TEACHER_GROUPS) { inclusive = true }
                    }
                })
            }
            composable(Routes.CREATE_CHALLENGE) {
                CreateChallengeScreen(
                    onBack = { navController.popBackStack() },
                    onPublish = {
                        navController.navigate(Routes.TEACHER_TASKS) {
                            popUpTo(Routes.TEACHER_GROUPS)
                            launchSingleTop = true
                        }
                    },
                )
            }
            composable(Routes.SUBMISSIONS) {
                SubmissionsScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
