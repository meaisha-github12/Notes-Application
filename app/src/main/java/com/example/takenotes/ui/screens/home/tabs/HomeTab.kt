package com.example.takenotes.ui.screens.home.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.takenotes.R
import com.example.takenotes.core.ApplicationClass
import com.example.takenotes.core.ThemePreferences
import com.example.takenotes.data.Notes
import com.example.takenotes.ui.screens.addupdatenote.AddUpdateNotesHere
import com.example.takenotes.ui.screens.components.NoteCard
import com.example.takenotes.ui.screens.home.VLRfontfamily
import com.example.takenotes.ui.theme.ColorPickerDialog
import kotlinx.coroutines.launch

@Composable
fun HomeTab(
    modifier: Modifier = Modifier,
    notes: List<Notes>,
    showDeleteConfirmation: Boolean = false,
    selectedNotes: List<Notes>,
    onNoteClick: (Notes) -> Unit,
    onNoteLongClick: (Notes) -> Unit,
    onDeleteClick: () -> Unit, // Renamed for clarity
    onColorPickerClick: () -> Unit,
    onCancelSelectionClick: () -> Unit,
    onCreateNoteClick:()->Unit

) {
    val navigator = LocalNavigator.currentOrThrow



    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp) // Consistent padding
                .background(Color(0xFFFBFCFD), shape = RoundedCornerShape(12.dp)) // Light background with rounded edges
        ) {
            if (selectedNotes.isNotEmpty()) {
                // Cancel Selection Icon
                IconButton(
                    modifier = Modifier.size(40.dp), // Consistent size
                    onClick = { onCancelSelectionClick() }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.cross),
                        contentDescription = "Cancel Selection",
                        tint = Color.Unspecified
                    )
                }

                // Delete Icon
                IconButton(
                    modifier = Modifier.size(40.dp), // Equal size
                    onClick = { onDeleteClick() }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.delete),
                        contentDescription = "Delete",
                        tint = Color.Unspecified
                    )
                }

                // Color Picker Icon
                IconButton(
                    modifier = Modifier.size(40.dp),
                    onClick = { onColorPickerClick() }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.wheel),
                        contentDescription = "Change Color",
                        modifier = Modifier.size(36.dp), // Slightly larger
                        tint = Color.Unspecified
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.padding(12.dp)
        )
        //            // FAVOURITES text Only
        Text(
            //////////////////////////////
            stringResource(id = R.string.hello_world),
            color = Color(0xFF92B0F8),
            fontFamily = VLRfontfamily,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 28.dp)
        )


        //    Spacer(modifier.padding(12.dp))
        // LazyVerticalStaggeredGrid Only
        LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Adaptive(120.dp), modifier = Modifier
            // weight here
            .weight(1f), content = {
            items(notes) { note ->

                NoteCard(note = note, selected = selectedNotes.contains(note),
                    onClick = {
                        onNoteClick(note)
                    }, onLongClick = {
                       onNoteLongClick(note)
                    })

            }
        })
        // Floating Button

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            FloatingActionButton(containerColor = Color(0xFF92B0F8),
                contentColor = Color.White,
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.padding(12.dp),
                onClick = {
                    onCreateNoteClick()
                }) {
                Row(
                    modifier = Modifier.padding(8.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_add_24),
                        contentDescription = "Add Vector Asset",
                        modifier = Modifier
                            .padding(4.dp)
                            .size(26.dp)
                    )
                    //  Spacer(modifier = Modifier.width(2.dp)) // Space between icon and te
                    Text(
                        text = "Create",
                        fontSize = 16.sp,
                        fontFamily = VLRfontfamily,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
        }
    }
}