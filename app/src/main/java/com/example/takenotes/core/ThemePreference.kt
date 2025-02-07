package com.example.takenotes.core

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

object ThemePreference {
    // Shared preferences

    // Initialization of SharedPreferences
    private fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences("ThemePreference", Context.MODE_PRIVATE)
    }

    // Function to save theme preference
    fun saveThemePreference(context: Context, themeMode: Int) {
        if(themeMode < 0 || themeMode > 2) {
            throw IllegalArgumentException("ONly 0,1,2 allowed")
        }
        val editor = getPreference(context).edit()
        editor.putInt("selected_theme_mode", themeMode)
        editor.apply()
    }

    // Function to get the current theme preference (true for dark mode, false for light mode)
    fun getThemePreference(context: Context): Int {
        return getPreference(context).getInt("selected_theme_mode", THEME_SYSTEM) // Default is light mode
    }

//    fun saveThemePreference(context: Context, themeMode: ThemeMode) {
//        val editor = getPreference(context).edit()
//        editor.putInt("selected_theme_mode", themeMode.ordinal)
//        editor.apply()
//    }
//
//    // Function to get the current theme preference (true for dark mode, false for light mode)
//    fun getThemePreferenceEnum(context: Context): ThemeMode {
//        val intValue = getPreference(context).getInt("selected_theme_mode", ThemeMode.System.ordinal) // Default is light mode
//        return ThemeMode.values().find { it.ordinal ==intValue }!!
//    }
}

const val THEME_SYSTEM = 0
const val THEME_LIGHT = 1
const val THEME_DARK = 2

@Composable
fun ThemeChanger(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Button(onClick = {
        ThemePreference.saveThemePreference(context, THEME_SYSTEM)
    }) {
        Text(text = "Set System")
    }

    Button(onClick = {
        ThemePreference.saveThemePreference(context, THEME_LIGHT)
    }) {
        Text(text = "Set Light")
    }

    Button(onClick = {
        ThemePreference.saveThemePreference(context, THEME_DARK)
    }) {
        Text(text = "Set Dark")
    }
}

enum class ThemeMode {
    System, Light, Dark
}