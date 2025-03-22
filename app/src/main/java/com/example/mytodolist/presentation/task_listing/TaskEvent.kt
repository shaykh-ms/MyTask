package com.example.mytodolist.presentation.task_listing

import com.example.mytodolist.domain.model.Task


sealed interface TaskEvent {
    data class TaskClicked(val todo : Task) : TaskEvent
    data object SheetDismiss: TaskEvent
    data object AddTask: TaskEvent
    data class QueryChange(val query : String) : TaskEvent
    data class SaveTask(val todo : Task) : TaskEvent
    data class DeleteTask(val todo: Task): TaskEvent
    data class StatusChange(val todo: Task): TaskEvent
}