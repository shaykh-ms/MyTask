package com.example.mytodolist.presentation.task_listing.sheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mytodolist.R

@Composable
fun MealInputItem(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = "Enter your task...",
                color = Color.LightGray
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.AddTask,
                contentDescription = "Enter Todo",
                tint = colorResource(R.color.blue_selected)
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.small,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(R.color.blue_selected),
            unfocusedBorderColor = colorResource(R.color.blue_selected),
        )
    )
}

@Composable
@Preview(showBackground = true)
fun MealInputItemPreview() {
    MealInputItem(
        text = "hello",
        onValueChange = {}
    )
}