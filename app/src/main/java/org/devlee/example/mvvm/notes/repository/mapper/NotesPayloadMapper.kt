package org.devlee.example.mvvm.notes.repository.mapper

import org.devlee.example.mvvm.notes.factory.NoteCaptionFactory
import org.devlee.example.mvvm.notes.repository.api.model.NotePayload
import org.devlee.example.mvvm.notes.repository.api.model.NotesResponsePayload
import org.devlee.example.mvvm.notes.repository.room.Note

class NotesPayloadMapper(
    private val factory: NoteCaptionFactory,
) {
    fun map(notesPayload: NotesResponsePayload): List<Note> = notesPayload.data.map(::map)

    fun map(notePayload: NotePayload) = Note(
        caption = factory.createCaption(),
        text = notePayload.noteText.trim()
    )
}