package com.example.mytodolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.mytodolist.presentation.task_listing.TaskListingScreen
import com.example.mytodolist.ui.theme.MyTodoListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTodoListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskListingScreen(context = this)
                }
            }
        }
    }
}