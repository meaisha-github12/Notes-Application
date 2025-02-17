package com.example.takenotes.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.takenotes.MainActivity
import java.util.Locale

fun updateLocale(context: Context, languageCode: String) {
    // 1) creating Locale object using Language code
    val localeObject = Locale(languageCode)
    // 2) setting a new locale as default for your application
    Locale.setDefault(localeObject)
    // 3) getting the current configuration from context's resource
    val configuration = context.resources.configuration
    // 4) updating this configuration with new locale
    configuration.setLocale(localeObject)
    // 5) Applying updated configuration to the resources
    context.resources.updateConfiguration(configuration, context.resources.displayMetrics)

}
fun restartApp(context: Context, activity: Activity?) {
    val intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
    activity?.finish() // Only finish if activity is valid


}