package com.example.mytodolist.data.mapper

import com.example.mytodolist.data.local.TaskEntity
import com.example.mytodolist.data.remote.dto.TaskDto
import com.example.mytodolist.domain.model.Task

fun TaskDto.toTask(): Task {
    return Task(
        id = id ?: 0,
        title = title ?: "",
        completed = completed ?: false

    )
}

fun TaskEntity.toTask(): Task {
    return Task(
        id = id ?: 0,
        title = title ?: "",
        completed = completed ?: false
    )
}

fun TaskDto.toTaskEntity(): TaskEntity {
    return TaskEntity(
        title = title ?: "",
        completed = completed ?: false
    )
}

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title ?: "",
        completed = completed ?: false
    )
}