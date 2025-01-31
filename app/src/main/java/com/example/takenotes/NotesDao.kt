package com.example.takenotes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {
    @Insert
    fun insertNote(note: Notes)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): List<Notes>
}