package org.devlee.example.mvvm.notes.repository

import kotlinx.coroutines.flow.Flow
import org.devlee.example.mvvm.notes.repository.room.Note
import org.devlee.example.mvvm.notes.repository.room.NotesDatabase

class Repository(
    private val db: NotesDatabase,
) {

    private val dao get() = db.notesDao

    fun getAll(): Flow<List<Note>> = dao.getAll()

    suspend fun save(note: Note) = dao.add(note)

    suspend fun delete(note: Note) = dao.delete(note)

}