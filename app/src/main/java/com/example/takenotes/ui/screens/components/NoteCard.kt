package com.example.takenotes.ui.screens.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.takenotes.R
import com.example.takenotes.core.ApplicationClass
import com.example.takenotes.data.Notes
import com.example.takenotes.ui.screens.home.VLRfontfamily
import com.example.takenotes.ui.screens.home.getRelativeTime
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    modifier: Modifier = Modifier, note: Notes,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    color: Color = Color(0xFF7793D6),
) {
    val coroutineScope = rememberCoroutineScope()
    val dao = ApplicationClass.getApp(LocalContext.current).dao
  //  var isFavourite by remember { mutableStateOf(note.favourite)}
    Box(
        modifier = modifier
            .padding(12.dp)
            .width(100.dp)
            .combinedClickable(onClick = onClick, onLongClick = onLongClick)
//            .combinedClickable(onClick = {
//                navigator.push(AddUpdateNotesHere(note))
//            }, onLongClick = {
//                selectedNoteToDelete = note
//            })
//                                .height((65 + (index % 5) * 30).dp)
            .background(
                color, shape = RoundedCornerShape(12.dp)
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
                    AsyncImage(
                        model = note.imageUrl,
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
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            val previewText = note.description.take(40) + "..."
            Text(
                text = previewText,
                modifier = Modifier.padding(8.dp),
                fontSize = 16.sp,
                fontFamily = VLRfontfamily,
                color = Color.White
            )
            Row( modifier = Modifier.fillMaxWidth()) {
            Text(
                text = getRelativeTime(note.updatedAt),
                modifier = Modifier.padding(8.dp),
                fontSize = 12.sp,
                fontFamily = VLRfontfamily,
                color = Color.White,
            )
            IconButton(onClick = {
                note.favourite =! note.favourite
                // Launch a coroutine to update the note in the database.
                coroutineScope.launch {
                    dao.updateNote(note)
                }

            }) {
                if(note.favourite)
                {
                    Icon(painter = painterResource(R.drawable.filledfav), contentDescription = "Favourite",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(18.dp)
                    )
                }
                else{

                    Icon(painter = painterResource(R.drawable.fav,), contentDescription ="Not favourite",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }}
        }
    }
}

@Preview
@Composable
fun NotePreview() {
    NoteCard(note = Notes(
        id = 123L,
        tittle = "Note Title",
        description = "Notes Description",
        updatedAt = System.currentTimeMillis(),
    ), onClick = {}, onLongClick = {})
}

@Preview
@Composable
fun NotePreview2() {
    NoteCard(note = Notes(
        id = 123L,
        tittle = "Note Title3",
        description = "Notes Description",
        updatedAt = System.currentTimeMillis(),
    ), onClick = {}, onLongClick = {}, color = Color.Red
    )
}