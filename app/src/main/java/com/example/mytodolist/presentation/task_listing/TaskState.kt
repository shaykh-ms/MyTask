package com.example.mytodolist.presentation.task_listing

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.mytodolist.domain.model.Task

data class TaskState(
    val tasks: SnapshotStateList<Task> = mutableStateListOf(),
    val clickedTask: Task? = null,
    val isLoading: Boolean = false,
    val query: String = "",
    val error: String? = null
)