package org.devlee.example.mvvm.notes.ui.main.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import org.devlee.example.mvvm.notes.repository.room.Note

class SwipeHelper(onSwiped: (Note) -> Unit,): ItemTouchHelper(SwipeCallback(onSwiped))