package com.example.takenotes.core

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
class ThemePreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    companion object {
        private const val PREFS_FILE_NAME = "MySharedPrefs"
        private const val THEME_KEY = "prefsKey"
    }
    //val defThemeMode = mutableStateOf("light")
    var defThemeMode : MutableState<String> = mutableStateOf("light")
    fun initialState() {
        val initialTheme =
            sharedPreferences.getString(THEME_KEY, "light") ?: "light"// by default it is light mode
        defThemeMode.value = initialTheme
    }
    fun savingData(userSelectedTheme: String) {
        val editor = sharedPreferences.edit()
        editor.putString(THEME_KEY, userSelectedTheme)
        editor.apply()
        Log.d("11222222", "Saved Theme: $userSelectedTheme")
        defThemeMode.value = userSelectedTheme
    }
    fun savedData()
    {
        val savedTheme = sharedPreferences.getString(THEME_KEY, "light")
        if (savedTheme != null) {
            defThemeMode.value = savedTheme
        }
    }
}