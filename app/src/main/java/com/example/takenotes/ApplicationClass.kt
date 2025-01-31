package com.example.takenotes

import android.app.Application
import android.content.Context
import androidx.room.Room

class ApplicationClass: Application() {
     private val db: AppDB by lazy{
        // creating instance of AppDB using Room's databaseBuilder method.
        Room.databaseBuilder(
         applicationContext,
            AppDB::class.java,
            "notes.db"

        )
            .build()
    }
     val dao: NotesDao by lazy{
        db.notesDao()
    }

    companion object {
        fun getApp(context: Context): ApplicationClass {
            return context.applicationContext as ApplicationClass
        }
    }
}