package org.devlee.example.mvvm.notes.ui.main

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.devlee.example.mvvm.notes.R
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
            addButton.setOnClickListener { saveNote() }
            searchView.addTextChangedListener { viewModel.filter(it.toString()) }
            searchButton.setOnClickListener { viewModel.toggleSearch() }
            swipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }
            sortButton.setOnClickListener { viewModel.toggleSort() }
        }

        viewModel.run {
            controlsState.onEach(::renderControlsState).launchIn(lifecycleScope)
            filteredNotesFlow.onEach(::renderNotes).launchIn(lifecycleScope)
            newCaption.onEach(::renderCaption).launchIn(lifecycleScope)
        }

    }

    private fun renderControlsState(state: MainViewControlsState) {
        renderLoading(state.isLoading)
        renderSearch(state.isSearchActivated)
        renderSortButton(state.isSortAscending)
    }

    private fun renderLoading(isLoading: Boolean) {
        views { swipeRefreshLayout.isRefreshing = isLoading }
    }

    private fun renderCaption(caption: String) {
        views { captionTextView.text = caption }
    }

    private fun renderNotes(notes: List<Note>) {
        adapter?.submitList(notes)
    }

    private fun renderSearch(isActivated: Boolean) {
        views {
            actionButtons.isVisible = !isActivated
            searchView.isVisible = isActivated
            if (isActivated) {
                searchView.requestFocus()
                searchButton.setImageResource(R.drawable.ic_baseline_close_24)
            } else {
                searchView.setText("")
                searchButton.setImageResource(R.drawable.ic_baseline_search_24)
            }
        }
    }

    private fun renderSortButton(isSortAscending: Boolean) {
        views {
            if (isSortAscending) {
                sortButton.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
            } else {
                sortButton.setImageResource(R.drawable.ic_baseline_arrow_downward_24)
            }
        }
    }

    private fun saveNote() {
        views {
            val noteText =
                addNoteEditText.text.toString().takeIf { it.isNotBlank() } ?: return@views

            viewModel.save(noteText)

            addNoteEditText.setText("")
        }
    }

    private fun <T> views(block: MainFragmentBinding.() -> T): T? = binding?.block()

}


