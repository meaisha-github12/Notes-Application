package com.example.takenotes.core

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.takenotes.data.AppDB
import com.example.takenotes.data.NotesDao

class ApplicationClass : Application() {

    lateinit var themePrefs: ThemePreferences

    override fun onCreate() {
        super.onCreate()
        // Initialize themePrefs safely
        //This themePrefs object can now be used to save or retrieve
        // the theme preference from SharedPreferences.
        themePrefs = ThemePreferences.getInstance(this.applicationContext)
        themePrefs.savedLanguage()
            // calling updateLocale at App Startup
    }

    // for DB
    private val db: AppDB by lazy {
        Room.databaseBuilder(
            applicationContext, AppDB::class.java, "notes.db"

        )
//            .addMigrations(object : Migration(1,2) {
//                override fun migrate(db: SupportSQLiteDatabase) {
//                    db.execSQL("ALTER TABLE notes ADD COLUMN updatedAt INTEGER NOT NULL DEFAULT 0")
//                }
//
//            })
            .fallbackToDestructiveMigration()
            .build()
    }
    val dao: NotesDao by lazy {
        db.notesDao()
    }

    companion object {
        fun getApp(context: Context): ApplicationClass {
            return context.applicationContext as ApplicationClass
        }
    }
}