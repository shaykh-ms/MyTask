package com.example.mytodolist.presentation.task_listing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodolist.data.mapper.toTaskEntity
import com.example.mytodolist.domain.repository.TaskRepository
import com.example.mytodolist.util.Resource
import com.example.mytodolist.util.analytics.AnalyticsEvent
import com.example.mytodolist.util.analytics.AnalyticsLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListingViewModel @Inject constructor(
    private val todoRepository: TaskRepository
) : ViewModel() {


    var state by mutableStateOf(TaskState())

    private val _uiEvent = MutableSharedFlow<TaskUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getTodoListing()
    }

    private fun getTodoListing() {
        viewModelScope.launch {
            todoRepository.getTodoListing().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        result.data?.let { listings ->
                            state = state.copy(
                                isLoading = false,
                                tasks = listings.toMutableStateList()
                            )
                        }
                    }
                }
            }
        }
    }


    fun onEvent(event: TaskEvent) {
        when (event) {
            TaskEvent.SheetDismiss -> {
                state = state.copy(
                    clickedTask = null,
                    query = ""
                )
            }

            is TaskEvent.TaskClicked -> {
                state = state.copy(
                    clickedTask = event.todo,
                    query = event.todo.title.orEmpty()
                )
                onUiEvent(TaskUiEvent.ShowSheet)
            }

            TaskEvent.AddTask -> {
                onUiEvent(TaskUiEvent.ShowSheet)
            }

            is TaskEvent.QueryChange -> {
                state = state.copy(
                    query = event.query
                )
            }

            is TaskEvent.SaveTask -> {
                viewModelScope.launch {
                    todoRepository.insertTodo(event.todo.toTaskEntity())
                }
            }

            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    todoRepository.deleteTodo(event.todo.toTaskEntity())
                }
            }

            is TaskEvent.StatusChange -> {
                viewModelScope.launch {
                    todoRepository.insertTodo(event.todo.toTaskEntity())
                }
            }
        }
    }


    private fun onUiEvent(uiEvent: TaskUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(uiEvent)
        }
    }
}