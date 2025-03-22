package com.example.mytodolist.di

import com.example.mytodolist.data.local.TaskDatabase
import com.example.mytodolist.data.remote.TaskApi
import com.example.mytodolist.data.repository.TaskRepositoryImpl
import com.example.mytodolist.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMealRepository(
        mealApi: TaskApi,
        db: TaskDatabase
    ): TaskRepository {
        return TaskRepositoryImpl(mealApi, db)
    }
}