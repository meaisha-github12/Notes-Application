package com.example.takenotes

import android.os.Build
import androidx.compose.ui.tooling.preview.Preview
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.AddUpdateNotesHere
import com.example.takenotes.ui.theme.TakeNotesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    lateinit var themePreferences: ThemePreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = ApplicationClass.getApp(this).dao

        lifecycleScope.launch(Dispatchers.IO) {
            dao.getAllNotesFlow().collect { notes ->
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Notes: ${notes.size}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        themePreferences = ThemePreferences(this)
        enableEdgeToEdge()
        themePreferences.savedData()
        setContent {
            val currentTheme = themePreferences.defThemeMode.value

            TakeNotesTheme(
                darkTheme = when (currentTheme) {
                    "light" -> false // If the saved theme is "light", set darkTheme to false (light mode)
                    "dark" -> true        // If the saved theme is "dark", set darkTheme to true (dark mode)
                    else -> isSystemInDarkTheme() // If no saved preference, fall back to the system theme
                }
            ) {
                Navigator(HomeScreen(themePreferences))
            }
        }
    }
}

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
    val navigator = LocalNavigator.currentOrThrow

    val context = LocalContext.current
    val dao = ApplicationClass.getApp(context).dao

    var selectedNoteToDelete by remember {
        mutableStateOf<Notes?>(null)
    }
    val coroutineScope = rememberCoroutineScope()

    var notesList = remember { mutableStateOf<List<Notes>>(emptyList()) }

    // Use a coroutine to load the notes from the database
    LaunchedEffect(Unit) {
        // Fetch notes in the IO dispatcher
        withContext(Dispatchers.IO) {
            dao.getAllNotesFlow().collect {
                notesList.value = it
            }
        }
    }

    if (selectedNoteToDelete != null) {
        AlertDialog(
            onDismissRequest = { selectedNoteToDelete = null },

            confirmButton = {
                Button(onClick = {
                    coroutineScope.launch(Dispatchers.IO) {
//                        val note = selectedNoteToDelete!!
//                        dao.deleteNote(note)
//                        selectedNoteToDelete =null

                        selectedNoteToDelete?.let {
                            dao.deleteNote(it)
                            selectedNoteToDelete = null
                        }
                    }
                }) {
                    Text(text = "Yes")
                }

            },
            dismissButton = {
                Button(onClick = {
                    selectedNoteToDelete = null
                }) {
                    Text(text = "No")
                }
            },
            title = { Text("Conformation") },
            text = { Text("Are you sure you want to delete ${selectedNoteToDelete!!.tittle}? ") },

            )
    }
    fun formatTimeStamp(timeStamp: Long): String {
        val simpleDateFormat = java.text.SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
        return simpleDateFormat.format(Date(timeStamp))

    }


    // val themePreference = ThemePreference(context)
    var checked by remember { mutableStateOf(true) }
    Box(modifier = modifier.padding(top = 46.dp)) {
        Column(Modifier.padding(12.dp)) {
            // Top bar with menu icon and profile picture
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(modifier = Modifier.size(48.dp), onClick = {
                    // Handle the click event here
                }) {
                    Icon(
                        painter = painterResource(R.drawable.menuicon),
                        contentDescription = "Menu Icon",
                        tint = Color.Unspecified
                    )

                }

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(R.drawable.girl),
                    contentDescription = "profile",
                    modifier = Modifier
                        .padding(end = 32.dp)
                        .size(48.dp)
                )
                switchButton(themePreferences)

            }
            Spacer(
                modifier = Modifier.padding(12.dp)

            )
            // FAVOURITES text Only
            Text(
                "Favourites",
                color = Color(0xFF92B0F8),
                fontFamily = VLRfontfamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 28.dp)
            )
            //    Spacer(modifier.padding(12.dp))
            // LazyVerticalStaggeredGrid Only
            LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Adaptive(120.dp),
                modifier = Modifier
                    // weight here
                    .weight(1f),
                content = {
                    items(notesList.value) { note ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .width(100.dp)
                                .combinedClickable(onClick = {
                                    navigator.push(AddUpdateNotesHere(note))
                                }, onLongClick = {
                                    selectedNoteToDelete = note
                                })
//                                .height((65 + (index % 5) * 30).dp)
                                .background(
                                    Color(0xFF7793D6), shape = RoundedCornerShape(12.dp)

                                )

                        ) {
                            Column {

                                Text(
                                    text = formatTimeStamp(note.updatedAt),
                                    modifier = Modifier.padding(8.dp),
                                    fontSize = 12.sp,
                                    fontFamily = VLRfontfamily,
                                    color = Color.White,
                                )
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

                            }
                        }

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
                    onClick = { /*TODO*/
                        navigator.push(AddUpdateNotesHere())
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


}


@Composable
fun switchButton(themePreferences: ThemePreferences) {
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    TakeNotesTheme {

    }
}
