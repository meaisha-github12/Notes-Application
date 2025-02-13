package com.example.takenotes.ui.screens.home.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.takenotes.R
import com.example.takenotes.core.ApplicationClass
import com.example.takenotes.data.Notes
import com.example.takenotes.ui.screens.addupdatenote.AddUpdateNotesHere
import com.example.takenotes.ui.screens.components.NoteCard
import com.example.takenotes.ui.screens.home.VLRfontfamily

@Composable
fun FavTab(modifier: Modifier = Modifier) {
    var selectedNoteToDelete by remember {mutableStateOf<Notes?>(null)}
    val navigator = LocalNavigator.currentOrThrow
    val FavouritesNotesFlow = ApplicationClass.getApp(LocalContext.current).dao.getFavouritesNotes()
    val favouritesNotes by FavouritesNotesFlow.collectAsState(initial = emptyList())
    Box(modifier = Modifier
        .fillMaxSize())
    {
        Column() {
            IconButton(modifier = Modifier.size(38.dp), onClick = {

            }) {
                Icon(
                    painter = painterResource(R.drawable.backarrow),
                    contentDescription = "Menu Icon",
                    tint = Color.Unspecified
                )

            }
            Spacer(modifier = Modifier.padding(12.dp))
            //            // FAVOURITES text Only
            Text(
                "Favourites",
                color = Color(0xFF92B0F8),
                fontFamily = VLRfontfamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 28.dp)
            )

   LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Adaptive(120.dp), modifier = Modifier.weight(1f),
       content = {
           items(favouritesNotes)
           {note ->
                NoteCard(note = note,
                    onClick = {
                   navigator.push(AddUpdateNotesHere(note))
                }, onLongClick =
                    {
                    }
                )


           }

       }
       )}
    }
}