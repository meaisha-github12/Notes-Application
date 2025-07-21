package com.example.takenotes.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
// Step 1
//  It just defines what a note is. It doesn't do anything, it just holds information
@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val tittle: String,
    val description: String,
    val favourite: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis(),
    val colors : Int = Color(0xFF92B0F8).toArgb(),
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val imageUrl: ByteArray? = null,



    )
