package org.devlee.example.mvvm.notes.ui.main.adapter

import androidx.recyclerview.widget.DiffUtil
import org.devlee.example.mvvm.notes.repository.room.Note

class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.text == newItem.text

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem

}