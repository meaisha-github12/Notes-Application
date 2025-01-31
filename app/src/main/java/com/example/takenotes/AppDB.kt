package com.example.takenotes

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1)
abstract class AppDB: RoomDatabase() {
abstract fun notesDao(): NotesDao
}