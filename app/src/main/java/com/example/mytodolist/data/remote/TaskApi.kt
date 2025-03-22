package com.example.mytodolist.data.remote

import com.example.mytodolist.data.remote.dto.TaskDto
import retrofit2.http.GET

interface TaskApi {

    @GET("todos")
    suspend fun getTodoListings(): List<TaskDto>

    companion object {
        const val BASE_URL = "https://dummy-json.mock.beeceptor.com/"
    }
}

