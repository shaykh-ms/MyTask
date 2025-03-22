package com.example.mytodolist.presentation.task_listing

sealed interface TaskUiEvent {
    data object ShowSheet : TaskUiEvent
}