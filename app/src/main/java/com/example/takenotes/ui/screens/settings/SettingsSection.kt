package com.example.takenotes.ui.screens.settings

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.takenotes.R
import com.example.takenotes.core.ApplicationClass
import com.example.takenotes.core.restartApp
import com.example.takenotes.core.updateLocale

@Composable
fun settingsTab() {

    val VLRfontfamily = FontFamily(Font(R.font.varelaroundregular))
    var context = LocalContext.current
    var themePreferences = ApplicationClass.getApp(context).themePrefs
    val activity = context as? Activity
    var expanded by remember { mutableStateOf(false) }
    var expandedThemes = remember { mutableStateOf(false) }
    Box(

    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                "Settings",
                modifier = Modifier.padding(4.dp),
                color = Color(0xFF92B0F8),
                fontFamily = VLRfontfamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,

            )
                Spacer(modifier = Modifier.padding(20.dp))
            Row(modifier = Modifier.padding(8.dp).clickable { expanded = !expanded }) {
                Icon(
                    painter = painterResource(R.drawable.language),
                    contentDescription = "Language Icon (more Options)",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    "Languages",
                    modifier = Modifier.padding(4.dp),
                    fontFamily = VLRfontfamily,
                    fontSize = 18.sp,
                     color = Color(0xFF92B0F8),
                )
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(text = { Text("Urdu") }, onClick = {
                    themePreferences.savingLanguage("ur")
                    updateLocale(context, "ur")
                    restartApp(activity)
                })
                DropdownMenuItem(text = { Text("English") }, onClick = {
                    themePreferences.savingLanguage("en")
                    updateLocale(context, "en")
                    restartApp(activity)
                })
                DropdownMenuItem(text = { Text("Arabic") }, onClick = {
                    themePreferences.savingLanguage("ar")
                    updateLocale(context, "ar")
                    restartApp(activity)
                })

            }

            Box(modifier = Modifier) {
                Row(modifier = Modifier.padding(8.dp).clickable {
                        expandedThemes.value = !expandedThemes.value
                    }) {


                    Icon(
                        painter = painterResource(R.drawable.theme),
                        contentDescription = "Theme Icon",
                        tint = Color.Unspecified
                    )
                    Text(
                        "App Mode",
                        fontFamily = VLRfontfamily,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(4.dp),
                        color = Color(0xFF92B0F8)

                    )
                }
                DropdownMenu(expanded = expandedThemes.value,
                    onDismissRequest = { expandedThemes.value = false }) {
                    DropdownMenuItem(text = { Text("Dark") }, onClick = {
                        themePreferences.savingData("dark")
                        expandedThemes.value = false
                    })
                    DropdownMenuItem(text = { Text("Light") }, onClick = {
                        themePreferences.savingData("light")
                        expandedThemes.value = false
                    })
                    DropdownMenuItem(text = { Text("System") }, onClick = {
                        themePreferences.savingData("system")
                        expandedThemes.value = false
                    })
                }


            }

        }

    }
}