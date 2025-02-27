package com.example.takenotes.data

import androidx.compose.ui.graphics.toArgb
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.takenotes.ui.theme.BlueColor

@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val tittle: String,
    val description: String,
    var favourite: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis(),
    val colors : Int= BlueColor.toArgb(),
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val imageUrl: ByteArray? = null,



    )
