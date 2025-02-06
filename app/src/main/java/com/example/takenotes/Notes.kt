package com.example.takenotes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val tittle: String,
    val description: String,
    val updatedAt: Long = System.currentTimeMillis()


)
