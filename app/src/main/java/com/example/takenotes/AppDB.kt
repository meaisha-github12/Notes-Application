package com.example.takenotes

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 2)
abstract class AppDB: RoomDatabase() {
abstract fun notesDao(): NotesDao
}