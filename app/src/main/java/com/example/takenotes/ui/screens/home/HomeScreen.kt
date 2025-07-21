package com.example.takenotes.ui.screens.home

import android.os.Build
import android.text.format.DateUtils
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.takenotes.R
import com.example.takenotes.core.ApplicationClass
import com.example.takenotes.core.ThemePreferences
import com.example.takenotes.data.Notes
import com.example.takenotes.ui.screens.addupdatenote.AddUpdateNotesHere
import com.example.takenotes.ui.screens.home.tabs.FavTab
import com.example.takenotes.ui.screens.home.tabs.HomeTab
import com.example.takenotes.ui.screens.settings.settingsTab
import com.example.takenotes.ui.theme.ColorPickerDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeScreen(val themePreferences: ThemePreferences) : Screen {
    @RequiresApi(Build.VERSION_CODES.Q)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            modifier = Modifier.fillMaxSize(
            )
        ) { paddingValues ->

            HomeView(
                modifier = Modifier.padding(paddingValues), themePreferences = themePreferences
            )

        }
    }
}

val VLRfontfamily = FontFamily(Font(R.font.varelaroundregular))

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeView(modifier: Modifier = Modifier, themePreferences: ThemePreferences) {
    val footerState = rememberSaveable { mutableStateOf(FooterContent.Home) }
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current
    val dao = ApplicationClass.getApp(context).dao
    var notesList = remember { mutableStateOf<List<Notes>>(emptyList()) }
    var isSelectionMode by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }
    var selectedNotes by remember { mutableStateOf<List<Notes>>(emptyList()) }
    var selectedColor by remember {
        mutableStateOf(if (selectedNotes.isNotEmpty()) Color(selectedNotes.first().colors) else Color.White)
    }
    val coroutineScope = rememberCoroutineScope()
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    // Use a coroutine to load the notes from the database
    LaunchedEffect(Unit) {
        // Fetch notes in the IO dispatcher
        withContext(Dispatchers.IO) {
            dao.getAllNotesFlow().collect {
                notesList.value = it
            }
        }
    }
    Box(modifier = modifier.padding(top = 12.dp)) {
        Column(Modifier.padding(12.dp)) {
            // Top bar with menu icon and profile picture

            Box(modifier = Modifier.weight(1f)) {
                when (footerState.value) {
                    FooterContent.Home -> {
                        HomeTab(
                            notes = notesList.value, //<-- Pass the actual list, not the MutableState
                            selectedNotes = selectedNotes,
                            onNoteClick = { clickedNote ->
                                if (isSelectionMode) {
                                    // Toggle the selection (add if not selected, remove if already selected)
                                    selectedNotes = if (selectedNotes.contains(clickedNote)) {
                                        selectedNotes - clickedNote
                                    } else {
                                        selectedNotes + clickedNote

                                    }
                                    // If no notes left selected, exit selection mode
                                    isSelectionMode = selectedNotes.isNotEmpty()
                                } else {
                                    navigator.push(AddUpdateNotesHere(clickedNote))
                                }
                            },
                            onNoteLongClick = { longClickedNote ->
                                isSelectionMode = true
                                selectedNotes = selectedNotes + longClickedNote
                            },
                            onDeleteClick = {
                                showDeleteConfirmation = true
                            },
                            onColorPickerClick = {
                                showColorPicker = true
                            },
                            onCancelSelectionClick = {
                                isSelectionMode = false
                                selectedNotes = emptyList()
                            },
                            onCreateNoteClick = {
                                navigator.push(AddUpdateNotesHere())
                            }


                            )

    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            confirmButton = {
                Button(onClick = {
                    coroutineScope.launch {
                        selectedNotes.forEach { note ->
                            dao.deleteNote(note)
                        }

                        showDeleteConfirmation = false
                        selectedNotes = emptyList()
                        isSelectionMode = false
                    }
                }) {
                    Text(text = "Yes")
                }
            },

            dismissButton = {
                Button(onClick = {
                    showDeleteConfirmation = false
                }) {
                    Text(text = "No")
                }
            },
            title = { Text("Conformation") },
            text = { Text("Are you sure you want to delete ${selectedNotes.size} notes? ") },
        )
    }
    if (showColorPicker)
    {
        ColorPickerDialog(
            onColorChange = { newColor ->
                selectedColor = newColor

                // Apply the color change to selected notes instantly
                coroutineScope.launch {
                    selectedNotes.forEach { note ->
                        dao.updateNote(note.copy(colors = selectedColor.hashCode()))
                    }

                    selectedNotes = emptyList()
                    isSelectionMode = false

                }
            },
            onDismiss = { showColorPicker = false }
        )


    }
                    }

                    FooterContent.Favourites -> {
                        FavTab()
                    }

                    FooterContent.Setting -> {
                        settingsTab()
                    }
                }
            }

            Footer(footerState.value, onChange = { newFc ->
                footerState.value = newFc
            })
        }
    }
}


//fun formatTimeStamp(timeStamp: Long): String {
//    val simpleDateFormat = java.text.SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
//    return simpleDateFormat.format(Date(timeStamp))
//}
fun getRelativeTime(timeStamp: Long): String {
    return DateUtils.getRelativeTimeSpanString(
        timeStamp,
        System.currentTimeMillis(),
        DateUtils.SECOND_IN_MILLIS,
    ).toString()
}

@Composable
fun ThemeSwitchButton(themePreferences: ThemePreferences) {
    val currentTheme by themePreferences.defThemeMode
    Switch(
        checked = currentTheme == "dark",  // Is the current theme dark? If yes, switch is on (checked)
        onCheckedChange = { isChecked -> // When the user toggles the switch
            val newThemeMode = if (isChecked) "dark" else "light" // Determine the new theme
            themePreferences.savingData(newThemeMode)  // Save the new theme mode in preferences
        },
        modifier = Modifier
            .size(50.dp)
            .padding(4.dp)
            .clip(CircleShape),
    )
}

//@Composable
//fun ThemeDropDown(themePreferences: ThemePreferences) {
//    var expanded = remember { mutableStateOf(false) }
//    Box(modifier = Modifier.padding()) {
//        IconButton(onClick = { expanded.value = !expanded.value }) {
//            Icon(painter = painterResource(R.drawable.theme), contentDescription = "Theme Icon", tint = Color.Unspecified)
//        }
//        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
//            DropdownMenuItem(text = { Text("Dark") }, onClick = {
//                themePreferences.savingData("dark")
//                expanded.value = false
//            })
//            DropdownMenuItem(text = { Text("Light") }, onClick = {
//                themePreferences.savingData("light")
//                expanded.value = false
//            })
//            DropdownMenuItem(text = { Text("System") }, onClick = {
//                themePreferences.savingData("system")
//                expanded.value = false })
//        }
//    }
//}

@Composable
fun Footer(
    footerState: FooterContent, onChange: (FooterContent) -> Unit = {}
) {
    Row(

        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xA97793D6),
                shape = RoundedCornerShape(12.dp),
            )
            .padding(4.dp)
    ) {
        FooterContent.entries.forEach { fc: FooterContent ->
            val selectedOption = fc == footerState
            val containerColor = if (selectedOption) Color(0xFF7793D6) else Color.Transparent
            val contentColor = if (selectedOption) Color.White else Color.Black
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = containerColor, contentColor = contentColor
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(),
                onClick = {
                    onChange(fc)

                }) {
                Text(
                    text = fc.name, fontFamily = VLRfontfamily, fontSize = 16.sp
                )

            }

        }

    }
}

enum class FooterContent {
    Home, Favourites, Setting
}
