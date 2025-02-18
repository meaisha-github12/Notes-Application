package com.example.takenotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.Navigator
import com.example.takenotes.core.ThemePreferences
import com.example.takenotes.core.updateLocale
import com.example.takenotes.ui.screens.home.HomeScreen
import com.example.takenotes.ui.theme.TakeNotesTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    lateinit var themePreferences: ThemePreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            val themePreferences = ThemePreferences(this)
            themePreferences.savedLanguage()
        updateLocale(this, themePreferences.defLanguage.value)

        // Ensure savedData() runs correctly
        lifecycleScope.launch {
            themePreferences.savedData()
        }
        enableEdgeToEdge()
        setContent {
            val currentTheme = themePreferences.defThemeMode.value
            TakeNotesTheme(
                darkTheme = when (currentTheme) {
                    "light" -> false
                    "dark" -> true
                    else -> isSystemInDarkTheme()
                }
            ) {
                Navigator(HomeScreen(themePreferences))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
}
