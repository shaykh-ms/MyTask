package com.example.mytodolist.presentation.task_listing

sealed interface TaskUiEvent {
    data object ShowSheet : TaskUiEvent
    data class ShowEmptyView(val isShow: Boolean): TaskUiEvent
}