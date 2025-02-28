package com.example.takenotes.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

// creating a Color Picker
@Composable
fun ColorPickerDialog(
    onDismiss: () -> Unit,
    onColorChange: (Color) -> Unit
) {
    val listOfColor = listOf(Color(0xFFE52020), Color(0xFFA8A196), Color.Black, Color(0xFF98D8EF), Color(0xFF5CB338),Color(0xFFFFEFC8), Color(0xFFE5D0AC), Color(0xFF66D2CE), Color(0xFF92B0F8))
    Dialog(onDismissRequest = { onDismiss() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOfColor.forEach { color ->
                Box(modifier = Modifier
                    .size(40.dp)
                    .background(color, CircleShape)
                    .clickable {
                        onColorChange(color)
                        onDismiss()
                    })
            }
        }
    }
}

