package com.example.tranquiljot.ui.screens.detailsScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.tranquiljot.data.NotesRepository

class DetailsScreenViewModel(
    private val notesRepository: NotesRepository,
    val id: Int
): ViewModel() {


}