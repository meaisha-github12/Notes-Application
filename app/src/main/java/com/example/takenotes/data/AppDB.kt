package com.example.takenotes.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 3)
abstract class AppDB: RoomDatabase() {
abstract fun notesDao(): NotesDao
}