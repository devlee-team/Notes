package org.devlee.example.mvvm.notes.repository.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getAll(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(note: List<Note>)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes WHERE text LIKE :searchText")
    fun getBySearchText(searchText: String): Flow<List<Note>>

}