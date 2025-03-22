package com.example.mytodolist.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM taskentity ORDER BY id DESC")
    fun getTodoList(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoListings(
        todoListingEntity: List<TaskEntity>
    )

    @Query("DELETE FROM taskentity")
    suspend fun clearTodoListings()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TaskEntity)

    @Delete
    suspend fun deleteTodo(todo: TaskEntity)

}