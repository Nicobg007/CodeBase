package com.nicolasbernal.codebase.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object TeacherState {
    val tasks = mutableStateListOf<TeacherTask>().apply { addAll(MockData.tasks) }
    var selectedTask by mutableStateOf("")

    fun publish(task: TeacherTask) {
        tasks.add(0, task)
    }
}
