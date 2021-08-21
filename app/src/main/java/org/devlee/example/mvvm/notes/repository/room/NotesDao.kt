package org.devlee.example.mvvm.notes.repository.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getAll(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(note: Note)

    @Delete
    suspend fun delete(note: Note)

}