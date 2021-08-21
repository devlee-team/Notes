package org.devlee.example.mvvm.notes.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabaseImpl : RoomDatabase(), NotesDatabase {

    companion object {
        fun create(context: Context) = Room
            .databaseBuilder(
                context,
                NotesDatabaseImpl::class.java,
                "notes-database"
            )
            .build()
    }
}

