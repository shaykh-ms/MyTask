package com.example.mytodolist.domain.repository

import com.example.mytodolist.data.local.TaskEntity
import com.example.mytodolist.domain.model.Task
import com.example.mytodolist.util.Resource
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun getTodoListing(): Flow<Resource<List<Task>>>

    suspend fun insertTodo(todo: TaskEntity)

    suspend fun deleteTodo(todo: TaskEntity)

}