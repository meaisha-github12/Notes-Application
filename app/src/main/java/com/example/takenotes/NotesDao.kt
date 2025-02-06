package com.example.takenotes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {
    @Insert
    suspend fun insertNote(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<Notes>

    @Update
    suspend fun updateNote(note: Notes)

}