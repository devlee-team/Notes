package org.devlee.example.mvvm.notes.repository.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotePayload(
    @SerialName("fact")
    val noteText: String
)