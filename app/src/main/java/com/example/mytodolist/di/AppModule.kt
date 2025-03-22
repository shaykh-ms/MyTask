package com.example.mytodolist.di

import android.app.Application
import androidx.room.Room
import com.example.mytodolist.data.local.TaskDatabase
import com.example.mytodolist.data.remote.TaskApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMealApi(): TaskApi {
        return Retrofit.Builder()
            .baseUrl(TaskApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(
            app,
            TaskDatabase::class.java,
            "todo_db"
        ).build()
    }

}