package org.devlee.example.mvvm.notes.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.devlee.example.mvvm.notes.databinding.MainFragmentBinding
import org.devlee.example.mvvm.notes.repository.room.Note
import org.devlee.example.mvvm.notes.ui.main.adapter.NotesAdapter
import org.devlee.example.mvvm.notes.ui.main.adapter.SwipeHelper

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private val adapter: NotesAdapter? get() = views { notesList.adapter as? NotesAdapter }
    private var binding: MainFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = MainFragmentBinding.inflate(inflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        views {
            notesList.adapter = NotesAdapter()
            SwipeHelper(viewModel::delete).attachToRecyclerView(notesList)
            addButton.setOnClickListener {
                saveNote()
            }
        }

        viewModel.notes.onEach(::renderNotes).launchIn(lifecycleScope)
        viewModel.newCaption.onEach(::renderCaption).launchIn(lifecycleScope)

    }

    private fun saveNote() {
        views {
            val noteText = addNoteEditText.text.toString().takeIf { it.isNotBlank() } ?: return@views

            viewModel.save(noteText)

            addNoteEditText.setText("")
        }
    }

    private fun renderCaption(caption: String) {
        views { captionTextView.text = caption }
    }

    private fun renderNotes(notes: List<Note>) {
        adapter?.submitList(notes)
    }

    private fun <T> views(block: MainFragmentBinding.() -> T): T? = binding?.block()

}


