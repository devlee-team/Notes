package org.devlee.example.mvvm.notes.repository.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val caption: String,
    val text: String
)


