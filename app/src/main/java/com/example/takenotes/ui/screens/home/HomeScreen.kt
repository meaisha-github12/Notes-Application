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
import com.example.takenotes.ui.screens.home.tabs.FavTab
import com.example.takenotes.ui.screens.home.tabs.HomeTab
import kotlinx.coroutines.Dispatchers
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
    val footerState = remember { mutableStateOf(FooterContent.Home) }
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current
    val dao = ApplicationClass.getApp(context).dao
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
    // val themePreference = ThemePreference(context)
    var checked by remember { mutableStateOf(true) }
    Box(modifier = modifier.padding(top = 12.dp)) {
        Column(Modifier.padding(12.dp)) {
            // Top bar with menu icon and profile picture

            Box(modifier = Modifier.weight(1f)){
                when(footerState.value){
                    FooterContent.Home -> {
                        HomeTab(
                            themePreferences = themePreferences,
                            notesList = notesList,
                        )
                    }
                    FooterContent.Favourites -> {
                        FavTab()
                    }
                    FooterContent.Setting -> {}
                }
            }

            Footer(footerState.value, onChange = {newFc ->
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

@Composable
fun Footer(
    footerState: FooterContent,
    onChange: (FooterContent) -> Unit = {}
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
                    containerColor = containerColor,
                    contentColor = contentColor
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(),
                onClick = {
                    onChange(fc)

                }) {
                Text(
                    text = fc.name,
                    fontFamily = VLRfontfamily,
                    fontSize = 16.sp
                )

            }

        }

    }
}

enum class FooterContent {
    Home, Favourites, Setting
}
