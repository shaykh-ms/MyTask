package com.example.mytodolist.presentation.task_listing.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytodolist.R
import com.example.mytodolist.domain.model.Task

@Composable
fun TodoItem2(
    todo: Task,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    onStatusChangeClick: (Boolean) -> Unit
) {

    val checkedState = remember { mutableStateOf(todo.completed) }

    Box(
        modifier = modifier
            .wrapContentSize()
            .background(colorResource(R.color.white))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()

        ) {

            Row(
                modifier = Modifier
            ) {



                Text(
                    modifier = Modifier,
                    text = todo.title ?: "",
                    style = TextStyle(
                        fontSize = 18.sp ,
                        fontWeight = FontWeight.Normal
                    ),
                    color = colorResource(R.color.blue_selected)
                )

                Spacer(modifier = Modifier.weight(1f))



                Spacer(modifier = Modifier.width(20.dp))





            }

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Left

            ) {


                Row(
                    modifier = Modifier
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 5.dp)
                            .clickable {
                                onDeleteClick.invoke()
                            },
                        tint = colorResource(R.color.red)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(
                        imageVector = Icons.Outlined.EditNote,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 5.dp)
                            .clickable {
                                onEditClick.invoke()
                            },
                        tint = colorResource(R.color.blue_selected)
                    )

                }





                Checkbox(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterVertically)
                    ,
                    checked = checkedState.value ?: false,
                    onCheckedChange = {
                        checkedState.value = it
                        onStatusChangeClick.invoke(it)

                    }
                )



            }






        }


    }

}

@Composable
@Preview
fun Show2() {
    TodoItem2(

        todo = Task(
            title = "Hel",
            id = 4,
            completed = true
        ),
        modifier = Modifier,
        onDeleteClick = {},
        onEditClick = {},
        onStatusChangeClick = {}
    )


}
