package com.example.takenotes

import android.app.Application
import android.content.Context
import androidx.room.Room

class ApplicationClass : Application() {

    lateinit var themePrefs: ThemePreferences

    override fun onCreate() {
        super.onCreate()
        // Initialize themePrefs safely
        //This themePrefs object can now be used to save or retrieve
        // the theme preference from SharedPreferences.
        themePrefs = ThemePreferences(this.applicationContext)
    }

    // for DB
    private val db: AppDB by lazy {
        Room.databaseBuilder(
            applicationContext, AppDB::class.java, "notes.db"

        ).build()
    }
    val dao: NotesDao by lazy {
        db.notesDao()
    }

    companion object {
        fun getApp(context: Context): ApplicationClass {

            val num = 200
            if (num > 300) {
                println("number is greater than 300")
            }
            else{
                println("number is less than 300")

            }

            return context.applicationContext as ApplicationClass
        }
    }
}