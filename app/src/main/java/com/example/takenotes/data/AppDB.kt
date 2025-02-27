package com.example.takenotes.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 4)
abstract class AppDB: RoomDatabase() {
abstract fun notesDao(): NotesDao
}