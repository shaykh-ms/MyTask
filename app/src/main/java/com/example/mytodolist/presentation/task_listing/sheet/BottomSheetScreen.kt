package com.example.mytodolist.presentation.task_listing.sheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytodolist.R
import com.example.mytodolist.domain.model.Task
import com.example.mytodolist.presentation.task_listing.TaskState

@Composable
fun BottomSheetScreen(
    modifier: Modifier = Modifier,
    todo: Task?,
    state: TaskState,
    enteredText: (String) -> Unit,
    addCallback: (Task) -> Unit,
    editCallBack: (Task) -> Unit
) {

    Box(
        modifier
            .padding(horizontal = 5.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                style = TextStyle(
                    color = colorResource(id = R.color.blue_selected),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                text = if (todo == null) "Add New Task" else "Edit Task",
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.height(32.dp))
            MealInputItem(
                modifier = Modifier
                    .imePadding(),
                text = state.query,
                onValueChange = {
                    enteredText(it)
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            CustomButtonItem(
                label = if (todo == null) "ADD" else "EDIT",
                onClick = {
                    if (todo == null && state.query.isNotEmpty()) {
                        addCallback.invoke(
                            Task(
                                completed = false,
                                title = state.query.trim()
                            )
                        )
                    } else {
                        val editedTodo = Task(
                            title = state.query.trim(),
                            completed = todo?.completed
                        )
                        editCallBack.invoke(
                            editedTodo
                        )
                    }
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BottomSheetScreenPreview() {
    BottomSheetScreen(
        todo = null,
        addCallback = {},
        editCallBack = {},
        enteredText = {},
        state = TaskState()
    )
}