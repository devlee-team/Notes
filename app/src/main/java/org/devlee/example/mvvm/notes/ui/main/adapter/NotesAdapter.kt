package org.devlee.example.mvvm.notes.ui.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.devlee.example.mvvm.notes.repository.room.Note

class NotesAdapter : ListAdapter<Note, NoteViewHolder>(NoteDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder.create(parent)

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) =
        holder.onBind(getItem(position))

}