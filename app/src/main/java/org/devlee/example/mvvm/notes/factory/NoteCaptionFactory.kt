package org.devlee.example.mvvm.notes.factory

import android.text.format.DateFormat
import org.devlee.example.mvvm.notes.repository.room.Note
import java.util.*

class NoteCaptionFactory {

    fun createCaption(): String =
        DateFormat.format("HH:mm:ss, MMM dd, yyyy", Date()).toString()

    fun createNote(noteText: String) = Note(
        caption = createCaption(),
        text = noteText
    )

}