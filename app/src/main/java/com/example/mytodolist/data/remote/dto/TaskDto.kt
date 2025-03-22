package com.example.mytodolist.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TaskDto(
    @SerializedName("userId")
    val userId: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("completed")
    val completed: Boolean?
)


