package com.example.takenotes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val tittle: String,
    val description: String,
    val favourite: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val imageUrl: ByteArray? = null,


)
