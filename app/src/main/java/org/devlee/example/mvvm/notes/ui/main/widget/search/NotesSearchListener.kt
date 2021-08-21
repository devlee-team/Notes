package org.devlee.example.mvvm.notes.ui.main.widget.search

import androidx.appcompat.widget.SearchView

class NotesSearchListener(
    private val onSearch: (String) -> Unit
) : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        onSearch(newText)
        return true
    }

}