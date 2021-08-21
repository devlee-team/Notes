package org.devlee.example.mvvm.notes.ui.main

data class MainViewControlsState(
    val isLoading: Boolean = false,
    val isSearchActivated: Boolean = false,
    val isSortAscending: Boolean = true,
)