package com.example.mytodolist.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.mytodolist.data.local.TaskDatabase
import com.example.mytodolist.data.local.TaskEntity
import com.example.mytodolist.data.mapper.toTask
import com.example.mytodolist.data.mapper.toTaskEntity
import com.example.mytodolist.data.remote.TaskApi
import com.example.mytodolist.domain.model.Task
import com.example.mytodolist.domain.repository.TaskRepository
import com.example.mytodolist.util.Resource
import com.example.mytodolist.util.writeLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val mealApi: TaskApi,
    db: TaskDatabase
) : TaskRepository {

    private val dao = db.dao

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getTodoListing(): Flow<Resource<List<Task>>> {
        return flow {
            emit(Resource.Loading(true))
            dao.getTodoList().collect { localListings ->
                emit(Resource.Success(
                    data = localListings.map { it.toTask() }
                ))

                val isDbEmpty = localListings.isEmpty()

                if (!isDbEmpty) {
                    writeLog("getTodoListing", "data from cache")
                    emit(Resource.Loading(false))
                    return@collect
                }

                val remoteListings = try {
                    mealApi.getTodoListings()
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error(e.message.toString()))
                    null
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(Resource.Error(e.message.toString()))
                    null
                }

                remoteListings?.let { remoteListings ->
                    dao.clearTodoListings()
                    dao.insertTodoListings(
                        remoteListings.map { it.toTaskEntity() }
                    )
                    writeLog("getTodoListing", "data from api")

                    dao.getTodoList().collect { lists ->
                        emit(Resource.Success(lists.map { it.toTask() }))
                    }
                    emit(Resource.Loading(false))
                }
            }
        }
    }

    override suspend fun insertTodo(todo: TaskEntity) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: TaskEntity) {
        dao.deleteTodo(todo)
    }
}