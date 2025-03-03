package com.example.takenotes.ui.screens.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.takenotes.R
import com.example.takenotes.core.ApplicationClass
import com.example.takenotes.data.Notes
import com.example.takenotes.ui.screens.addupdatenote.byteArrayToBitmap
import com.example.takenotes.ui.screens.home.VLRfontfamily
import com.example.takenotes.ui.screens.home.getRelativeTime
import com.example.takenotes.utils.foregroundColor
import kotlinx.coroutines.launch

val bitmapCache = hashMapOf<Long, ImageBitmap>()

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    note: Notes,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val dao = ApplicationClass.getApp(LocalContext.current).dao
    //  var isFavourite by remember { mutableStateOf(note.favourite)}
    val bgColor = Color(note.colors)
    Box(
        modifier = modifier
            .padding(12.dp)
            .width(100.dp)
            .combinedClickable(onClick = onClick, onLongClick = onLongClick)
            .background(
                bgColor, shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = if (selected) 4.dp else 0.dp,  // No border when unselected
                color = if (selected) Color.Black else Color.Transparent // Invisible border when unselected
            )

    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(4.dp)
            ) {
                if (note.imageUrl != null) {
                    Image(
                        bitmapCache[note.id] ?: byteArrayToBitmap(note.imageUrl).asImageBitmap()
                            .also {
                                bitmapCache[note.id] = it
                            },
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(100.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Text(
                text = note.tittle,
                modifier = Modifier.padding(8.dp),
                fontSize = 18.sp,
                fontFamily = VLRfontfamily,
                color = bgColor.foregroundColor(),
                fontWeight = FontWeight.Bold
            )
            val previewText = note.description.take(40) + "..."
            Text(
                text = previewText,
                modifier = Modifier.padding(8.dp),
                fontSize = 16.sp,
                fontFamily = VLRfontfamily,
                color = bgColor.foregroundColor(),
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = getRelativeTime(note.updatedAt),
                    modifier = Modifier.padding(8.dp),
                    fontSize = 12.sp,
                    fontFamily = VLRfontfamily,
                    color = bgColor.foregroundColor(),
                )
                IconButton(onClick = {
                    // Launch a coroutine to update the note in the database.
                    coroutineScope.launch {
                        val newNote = note.copy(favourite = !note.favourite)
                        dao.updateNote(newNote)
                    }

                }) {
                    if (note.favourite) {
                        Icon(
                            painter = painterResource(R.drawable.filledfav),
                            contentDescription = "Favourite",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(18.dp)
                        )
                    } else {

                        Icon(
                            painter = painterResource(R.drawable.fav),
                            contentDescription = "Not favourite",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Add Color Picker Row

        }
        //Show Color Picker Dialog when button is clicked
//        if(showColorPicker){
//            ColorPickerDialog(
//                noteId = note.id,
//                onColorChange = { selectedColor ->
//                    onColorChange(note.id , selectedColor.hashCode())
//                    showColorPicker = false
//
//                },
//                onDismiss = { showColorPicker = false }
//            )
//
//        }
    }
}


