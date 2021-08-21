package org.devlee.example.mvvm.notes.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.devlee.example.mvvm.notes.repository.api.NotesApi
import org.devlee.example.mvvm.notes.repository.mapper.NotesPayloadMapper
import org.devlee.example.mvvm.notes.repository.room.Note
import org.devlee.example.mvvm.notes.repository.room.NotesDatabase

class Repository(
    private val db: NotesDatabase,
    private val api: NotesApi,
    private val mapper: NotesPayloadMapper,
) {

    private val dao get() = db.notesDao

    fun getAll(): Flow<List<Note>> = dao.getAll()

    suspend fun save(note: Note) = dao.add(note)

    suspend fun delete(note: Note) = dao.delete(note)

    suspend fun reloadNotes() = runCatching {
        val payload = api.notes()
        val notes = mapper.map(payload)
        dao.add(notes)
    }
}

