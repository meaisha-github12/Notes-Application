package com.example.takenotes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.Navigator
import com.example.takenotes.core.ApplicationClass
import com.example.takenotes.core.ThemePreferences
import com.example.takenotes.ui.screens.home.HomeScreen
import com.example.takenotes.ui.theme.TakeNotesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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


