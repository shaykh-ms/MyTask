package com.example.mytodolist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    val title: String,
    val completed: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
)
