package com.example.mytodolist.presentation.task_listing

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mytodolist.R
import com.example.mytodolist.domain.model.Task
import com.example.mytodolist.presentation.task_listing.components.TodoItem2
import com.example.mytodolist.presentation.task_listing.sheet.BottomSheetScreen
import com.example.mytodolist.util.analytics.AnalyticsEvent
import com.example.mytodolist.util.analytics.AnalyticsLogger
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskListingScreen(
    context: Context,
    viewModel: TaskListingViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val onEvent = viewModel::onEvent
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest {
            when (it) {
                TaskUiEvent.ShowSheet -> {
                    isSheetOpen = true
                    sheetState.show()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(TaskEvent.AddTask)
                },
                containerColor = colorResource(R.color.blue_selected)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add note",
                    tint = Color.White
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
                .padding(16.dp)
                .padding(bottom = 32.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 8.dp),
                horizontalArrangement = Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Task List",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.blue_selected)
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                state = listState
            ) {
                items(
                    state.tasks,
                    key = {
                        it.id.toString()
                    }) { todo ->
                    TodoItem2(
                        todo = todo,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 24.dp,
                                bottom = 8.dp,
                                start = 16.dp,
                                end = 16.dp
                            ),
                        onDeleteClick = {
                            scope.launch {
                                onEvent(TaskEvent.DeleteTask(todo))
                                Toast.makeText(context, "Task Deleted.", Toast.LENGTH_SHORT).show()
                            }
                        },
                        onEditClick = {
                            onEvent(TaskEvent.TaskClicked(todo))
                        },
                        onStatusChangeClick = {
                            onEvent(TaskEvent.StatusChange(todo.copy(completed = it)))
                            if (it) {
                                AnalyticsLogger.updateLog(AnalyticsEvent.TASK_COMPLETED)
                                Toast.makeText(context, "Task Completed.", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(0.5.dp)
                            .background(Color.LightGray)
                    ) {
                    }
                }
            }
            LaunchedEffect(state.tasks) {
                if (state.tasks.isNotEmpty()) {
                    listState.animateScrollToItem(0)
                }
            }
        }
    }
    if (isSheetOpen) {
        ModalBottomSheet(
            modifier = Modifier
                .safeContentPadding(),
            sheetState = sheetState,
            windowInsets = WindowInsets.ime,
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                    isSheetOpen = false
                    onEvent(TaskEvent.SheetDismiss)
                }
            },
        ) {
            BottomSheetScreen(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 50.dp
                ),
                todo = state.clickedTask,
                state = viewModel.state,
                enteredText = {
                    viewModel.state = viewModel.state.copy(
                        query = it
                    )
                },
                addCallback = {
                    val todo = Task(title = state.query, completed = false)
                    isSheetOpen = false
                    onEvent(TaskEvent.SaveTask(todo))
                    onEvent(TaskEvent.SheetDismiss)
                    AnalyticsLogger.updateLog(AnalyticsEvent.TASK_ADDED)
                    Toast.makeText(context, "Task Added.", Toast.LENGTH_SHORT).show()
                },
                editCallBack = {
                    state.clickedTask?.copy(title = state.query)?.let {
                        onEvent(TaskEvent.SaveTask(it))
                    }
                    onEvent(TaskEvent.SheetDismiss)
                    isSheetOpen = false
                    AnalyticsLogger.updateLog(AnalyticsEvent.TASK_EDITED)
                    Toast.makeText(context, "Task Updated.", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error
            )
        }

    }
}