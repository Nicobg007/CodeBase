package com.nicolasbernal.codebase.data

object MockData {

    val pathNodes = listOf(
        PathNode("1. Variables", LessonState.DONE, "📦"),
        PathNode("2. Operadores", LessonState.CURRENT, "➕"),
        PathNode("3. Condicionales", LessonState.AVAILABLE, "🔀"),
        PathNode("4. Ciclo For", LessonState.LOCKED, "🔁"),
        PathNode("Cofre Especial", LessonState.LOCKED, "🎁"),
        PathNode("5. Funciones", LessonState.LOCKED, "⚙️"),
    )

    val questions = listOf(
        Question(
            prompt = "¿Cuál es el valor final de la variable total?",
            code = """
                fun main() {
                    val vidas = 3
                    val bonus = 2
                    val total = vidas + bonus
                    println(total)
                }
            """.trimIndent(),
            options = listOf("3", "5", "6", "32"),
            correctIndex = 1,
            explanation = "Kotlin suma las variables numéricas: 3 (vidas) + 2 (bonus) = 5.",
        ),
        Question(
            prompt = "¿Qué palabra clave declara una variable que NO se puede reasignar?",
            code = "___ edad = 18",
            options = listOf("var", "val", "let", "const"),
            correctIndex = 1,
            explanation = "En Kotlin, val crea una variable de solo lectura (inmutable).",
        ),
        Question(
            prompt = "¿Qué imprime este código?",
            code = """
                var puntos = 10
                puntos = puntos + 5
                println(puntos)
            """.trimIndent(),
            options = listOf("10", "5", "15", "Error"),
            correctIndex = 2,
            explanation = "puntos es var, así que se puede reasignar: 10 + 5 = 15.",
        ),
    )

    val achievements = listOf(
        Achievement("Racha de Fuego", "Mantén 7 días de racha", 1f, true),
        Achievement("Primer val en Kotlin", "Crea tu primera variable inmutable", 1f, true),
        Achievement("Modo Relámpago", "5 lecciones en un día", 0.6f, false),
    )

    // ---------- Profesor ----------
    val groups = listOf(
        ClassGroup("Grupo A: Intro Kotlin", 25, 0.90f),
        ClassGroup("Grupo B: Android Básica", 24, 0.72f),
    )

    val alerts = listOf(
        StudentAlert("Mateo Gómez", "Atascado: Ejercicio 4 (val vs var)"),
        StudentAlert("Lucía Rivas", "Inactiva 2 días consecutivos"),
    )

    val tasks = listOf(
        TeacherTask("Desafío: Cajas fijas vs mutables", "Módulo 2 · Variables en Kotlin", 22, 28, "RETO RÁPIDO"),
        TeacherTask("Mini-Examen Relámpago: Sintaxis y funciones", "Programado · Jueves 12:00", 0, 28, "POR PUBLICAR"),
        TeacherTask("Práctica de Casa: Operadores y expresiones", "Completada · Semana 3", 28, 28, "COMPLETADA"),
    )

    val submissions = listOf(
        Submission("Mateo Gómez", "Hace 2h · 100% test passing", "Aprobado (1er intento)", 50),
        Submission("Sofía Martínez", "Hace 6h · 100% test passing", "Aprobado (1er intento)", 50),
        Submission("Diego A.", "Hace 4h · 2 ejecuciones", "Aprobado (2 intentos)", 45),
        Submission("Lucía Rivas", "2 días inactiva", "Sin entrega", null),
        Submission("Andrés P.", "Vence hoy 23:59", "Pendiente", null),
    )
}
