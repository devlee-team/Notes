package org.devlee.example.mvvm.notes.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.devlee.example.mvvm.notes.locator.locateLazy
import org.devlee.example.mvvm.notes.factory.NoteCaptionFactory
import org.devlee.example.mvvm.notes.repository.Repository
import org.devlee.example.mvvm.notes.repository.room.Note
import org.devlee.example.mvvm.notes.utils.containsTags

class MainViewModel : ViewModel() {

    private val repository: Repository by locateLazy()
    private val factory: NoteCaptionFactory by locateLazy()
    private val notesFlow = MutableStateFlow<List<Note>>(emptyList())
    private val _controlsState = MutableStateFlow(MainViewControlsState())

    private val searchTextFlow = MutableStateFlow("")

    val controlsState: Flow<MainViewControlsState> = _controlsState
    val newCaption = captionsFlow()
    val filteredNotesFlow = MutableStateFlow<List<Note>>(emptyList())

    init {
        refresh()

        repository.getAll().onEach { notesFlow.value = it }.launchIn(viewModelScope)

        controlsState
            .onEach { updateFilteredNotes(searchTextFlow.value) }
            .launchIn(viewModelScope)

        searchTextFlow
            .debounce(250)
            .distinctUntilChanged()
            .onEach(::updateFilteredNotes)
            .launchIn(viewModelScope)

        notesFlow
            .onEach { updateFilteredNotes(searchTextFlow.value) }
            .launchIn(viewModelScope)
    }

    fun refresh() {
        viewModelScope.launch {
            controlsState { copy(isLoading = true) }
            repository.reloadNotes()
            controlsState { copy(isLoading = false) }
        }
    }


    fun filter(searchText: String) {
        searchTextFlow.value = searchText
    }

    fun save(note: String) {
        viewModelScope.launch { repository.save(factory.createNote(note)) }
    }

    fun delete(note: Note) {
        viewModelScope.launch { repository.delete(note) }
    }

    fun toggleSearch() {
        controlsState { copy(isSearchActivated = !isSearchActivated) }
    }

    fun toggleSort() {
        controlsState { copy(isSortAscending = !isSortAscending) }
    }

    private fun controlsState(modifier: MainViewControlsState.() -> MainViewControlsState) {
        _controlsState.value = _controlsState.value.modifier()
    }

    private fun updateFilteredNotes(searchText: String) {
        filteredNotesFlow.value = if (searchText.length > 2) {
            notesFlow.value.filter { it.text.containsTags(searchText) }
        } else {
            notesFlow.value
        }.let { notes ->
            if (_controlsState.value.isSortAscending) {
                notes.sortedBy { it.text }
            } else {
                notes.sortedByDescending { it.text }
            }
        }
    }

    private fun captionsFlow() = flow<String> {
        while (true) {
            emit(factory.createCaption())
            delay(500L)
        }
    }

}
