package com.example.mytodolist.presentation.task_listing.sheet

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytodolist.R

@Composable
fun CustomButtonItem(
    label: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(1.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.blue_selected), // Solid background color
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = label)
    }
}


@Composable
@Preview(showBackground = true)
fun CustomButtonItemPreview() {
    CustomButtonItem(label = "Add", onClick = {})
}