package org.devlee.example.mvvm.notes.repository.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotesResponsePayload(
    @SerialName("data")
    val data: List<NotePayload>
)