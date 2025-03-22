package com.example.mytodolist.domain.model


data class Task(
    val id: Int? = null,
    val title: String? = null,
    val completed: Boolean? = null
)