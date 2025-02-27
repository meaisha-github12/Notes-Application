package com.example.takenotes.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

fun Color.foregroundColor(): Color {
    return if(luminance() > 0.5) {
        Color.Black
    } else {
        Color.White
    }
}