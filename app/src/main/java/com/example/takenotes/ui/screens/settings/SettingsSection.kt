package com.example.takenotes.ui.screens.settings

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.takenotes.R
import com.example.takenotes.core.ThemePreferences
import com.example.takenotes.core.restartApp
import com.example.takenotes.core.updateLocale

@Composable
fun settingsTab(themePreferences: ThemePreferences)
{
    var context = LocalContext.current
    var themePreferences = ThemePreferences(context)
    val activity = context as? Activity
    var expanded by remember { mutableStateOf(false) }
    Box(

    )
    {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(painter = painterResource(R.drawable.language), contentDescription = "Language Icon (more Options)"
                , tint = Color.Unspecified
                )

            }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded =false }) {
            DropdownMenuItem(text = { Text("Urdu") }, onClick = {
                themePreferences.savingLanguage("ur")
                updateLocale(context,"ur")
                restartApp(context, activity)
            })
            DropdownMenuItem(text = { Text("English") }, onClick = { themePreferences.savingLanguage("en")
            updateLocale(context,"en")
                restartApp(context, activity)
            })
            DropdownMenuItem(text = { Text("Arabic" )}, onClick = { themePreferences.savingLanguage("ar")
            updateLocale(context,"ar")
                restartApp(context, activity)
            })
            
        }
    }
}