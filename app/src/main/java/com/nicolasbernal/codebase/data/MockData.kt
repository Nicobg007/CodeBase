package com.nicolasbernal.codebase.data

object MockData {

    val lessons = listOf(
        // ---------------- 1. VARIABLES ----------------
        Lesson(
            title = "Variables",
            emoji = "📦",
            headline = "¿Cómo funciona una variable?",
            explanation = "En Kotlin usamos val para cajas fijas que no cambian y var para cajas cuyo contenido puede modificarse.",
            example = """
                fun main() {
                    val nombre = "Alex"
                    var puntos = 100
                    puntos = 150
                    println("Hola Alex")
                }
            """.trimIndent(),
            tip = "Usa siempre val por defecto. Usa var solo cuando realmente necesites reasignar el valor.",
            questions = listOf(
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
            ),
        ),

        // ---------------- 2. OPERADORES ----------------
        Lesson(
            title = "Operadores",
            emoji = "➕",
            headline = "Haz cálculos con operadores",
            explanation = "Los operadores permiten calcular: + suma, - resta, * multiplica, / divide y % da el residuo de una división. Con números enteros, / descarta los decimales.",
            example = """
                val a = 7
                val b = 2
                println(a + b)  // 9
                println(a / b)  // 3
                println(a % b)  // 1
            """.trimIndent(),
            tip = "Dividir dos Int da un Int: 7 / 2 = 3. Usa 7 / 2.0 para obtener 3.5.",
            questions = listOf(
                Question(
                    prompt = "¿Qué imprime este código?",
                    code = """
                        val a = 7
                        val b = 2
                        println(a / b)
                    """.trimIndent(),
                    options = listOf("3.5", "3", "4", "Error"),
                    correctIndex = 1,
                    explanation = "Con enteros, / descarta los decimales: 7 / 2 = 3.",
                ),
                Question(
                    prompt = "¿Qué devuelve el operador %?",
                    code = "println(10 % 3)",
                    options = listOf("0", "1", "3", "3.33"),
                    correctIndex = 1,
                    explanation = "% devuelve el residuo: 10 = 3 × 3 + 1, así que el residuo es 1.",
                ),
                Question(
                    type = QuestionType.ORDER,
                    prompt = "Ordena el código para imprimir la suma de 4 y 5",
                    options = listOf("val x = 4", "val y = 5", "println(x + y)"),
                    explanation = "Primero se declaran x e y; después se imprime x + y, que da 9.",
                ),
            ),
        ),

        // ---------------- 3. CONDICIONALES ----------------
        Lesson(
            title = "Condicionales",
            emoji = "🔀",
            headline = "Toma decisiones con if",
            explanation = "if evalúa una condición y ejecuta un bloque solo si es verdadera. Puedes añadir else para el caso contrario.",
            example = """
                val nota = 80
                if (nota >= 60) {
                    println("Aprobado")
                } else {
                    println("Reprobado")
                }
            """.trimIndent(),
            tip = "Combina condiciones con && (y), || (o) y ! (no).",
            questions = listOf(
                Question(
                    prompt = "¿Qué imprime este código?",
                    code = """
                        val edad = 17
                        if (edad >= 18) println("Mayor") else println("Menor")
                    """.trimIndent(),
                    options = listOf("Mayor", "Menor", "17", "Error"),
                    correctIndex = 1,
                    explanation = "17 no es mayor o igual que 18, así que se ejecuta el else: Menor.",
                ),
                Question(
                    prompt = "¿Qué operador significa «y» lógico en Kotlin?",
                    options = listOf("&&", "||", "!", "=="),
                    correctIndex = 0,
                    explanation = "&& es el «y»: la condición es verdadera solo si ambos lados lo son.",
                ),
                Question(
                    type = QuestionType.ORDER,
                    prompt = "Ordena el if completo",
                    options = listOf(
                        "val nota = 80",
                        "if (nota >= 60) {",
                        "    println(\"Aprobado\")",
                        "}",
                    ),
                    explanation = "Se declara la nota, se abre el if con su condición, se escribe el bloque y se cierra.",
                ),
            ),
        ),

        // ---------------- 4. CICLO FOR ----------------
        Lesson(
            title = "Ciclo For",
            emoji = "🔁",
            headline = "Repite tareas con for",
            explanation = "for repite un bloque por cada valor de un rango. 1..5 incluye el 1 y el 5.",
            example = """
                for (i in 1..3) {
                    println(i)
                }
            """.trimIndent(),
            tip = "Usa until para excluir el final: 1 until 5 va de 1 a 4.",
            questions = listOf(
                Question(
                    prompt = "¿Qué imprime este código?",
                    code = """
                        for (i in 1..3) {
                            print(i)
                        }
                    """.trimIndent(),
                    options = listOf("123", "012", "321", "3"),
                    correctIndex = 0,
                    explanation = "i toma los valores 1, 2 y 3; print los escribe seguidos: 123.",
                ),
                Question(
                    prompt = "¿Cuántas veces se repite este ciclo?",
                    code = """
                        for (i in 1..5) {
                            println("Hola")
                        }
                    """.trimIndent(),
                    options = listOf("4", "5", "6", "Infinitas"),
                    correctIndex = 1,
                    explanation = "El rango 1..5 incluye ambos extremos: son 5 repeticiones.",
                ),
                Question(
                    type = QuestionType.ORDER,
                    prompt = "Ordena el ciclo for",
                    options = listOf("for (i in 1..3) {", "    println(i)", "}"),
                    explanation = "Primero la cabecera del for, luego el cuerpo y al final la llave de cierre.",
                ),
            ),
        ),

        // ---------------- 5. FUNCIONES ----------------
        Lesson(
            title = "Funciones",
            emoji = "⚙️",
            headline = "Reutiliza código con funciones",
            explanation = "Una función es un bloque de código con nombre que puedes reutilizar. Se declara con fun, puede recibir parámetros y devolver un valor.",
            example = """
                fun doble(n: Int): Int {
                    return n * 2
                }

                fun main() {
                    println(doble(4))  // 8
                }
            """.trimIndent(),
            tip = "Si la función es una sola expresión puedes escribir: fun doble(n: Int) = n * 2",
            questions = listOf(
                Question(
                    prompt = "¿Qué imprime este código?",
                    code = """
                        fun doble(n: Int) = n * 2

                        fun main() {
                            println(doble(4))
                        }
                    """.trimIndent(),
                    options = listOf("4", "8", "44", "Error"),
                    correctIndex = 1,
                    explanation = "doble(4) devuelve 4 * 2 = 8.",
                ),
                Question(
                    prompt = "¿Qué palabra clave declara una función en Kotlin?",
                    options = listOf("def", "function", "fun", "func"),
                    correctIndex = 2,
                    explanation = "En Kotlin las funciones se declaran con la palabra fun.",
                ),
                Question(
                    type = QuestionType.ORDER,
                    prompt = "Ordena la función saludar",
                    options = listOf(
                        "fun saludar(nombre: String) {",
                        "    println(\"Hola \" + nombre)",
                        "}",
                    ),
                    explanation = "Se declara la función con su parámetro, se escribe el cuerpo y se cierra la llave.",
                ),
            ),
        ),
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
