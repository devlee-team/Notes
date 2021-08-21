package org.devlee.example.mvvm.notes.repository.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey val text: String,
    val caption: String
)


