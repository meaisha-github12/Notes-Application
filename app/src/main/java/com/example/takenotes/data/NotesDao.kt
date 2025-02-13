package com.example.takenotes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Insert
    suspend fun insertNote(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<Notes>

    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
     fun getAllNotesFlow(): Flow<List<Notes>>

    @Query("Update notes SET favourite = :isFavourite where id = :noteId")
    suspend fun updateFavouritesNotes(noteId: Long, isFavourite: Boolean)

    @Update
    suspend fun updateNote(note: Notes)

    @Query("SELECT * FROM notes WHERE favourite = 1")
    fun getFavouritesNotes(): Flow<List<Notes>>


}