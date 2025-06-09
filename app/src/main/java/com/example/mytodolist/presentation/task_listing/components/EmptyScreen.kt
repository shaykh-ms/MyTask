package com.example.mytodolist.presentation.task_listing.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytodolist.R
import com.example.mytodolist.domain.model.Task


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyScreen(
    modifier: Modifier
) {
    Scaffold(
       /* topBar = {
            TopAppBar(
                title = { Text("ToDo App") }
            )
        }*/
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No Tasks Available",
                    style = MaterialTheme.typography.headlineMedium,
                    color = colorResource(R.color.blue_selected)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Add a new task to get started!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorResource(R.color.blue_selected).copy(alpha = 0.7f)
                )
            }
        }
    }
}







@Composable
@Preview
fun EmptyScreenPreview() {
    EmptyScreen(
        modifier = Modifier,
    )


}