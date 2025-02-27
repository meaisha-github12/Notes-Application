package com.example.takenotes.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// creating a Color Picker
@Composable
fun ColorPickerDialog(
                       onDismiss: () -> Unit,
                       onColorChange: (Color) -> Unit ) {
    val listOfColor = listOf(Color.Red, Color.Gray, Color.Black, Color.Blue, Color.Green, Color.Yellow)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOfColor.forEach { color ->
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color, CircleShape)
                    .clickable {
                        onColorChange(color)
                        onDismiss()
                    }
            )
        }
    }
}

