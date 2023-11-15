package com.example.tranquiljot.ui.screens

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tranquiljot.NotesApplication
import com.example.tranquiljot.ui.screens.detailsScreen.DetailsScreenViewModel
import com.example.tranquiljot.ui.screens.entryScreen.EntryScreenViewModel
import com.example.tranquiljot.ui.screens.homeScreen.HomeScreenViewModel

object AppViewModelInitializer {
    val Factory = viewModelFactory {
        initializer {
            HomeScreenViewModel(
                notesRepository = notesApplication().container.notesRepository
            )
        }
        initializer {
            EntryScreenViewModel(
                notesRepository = notesApplication().container.notesRepository
            )
        }
    }
}
fun CreationExtras.notesApplication() : NotesApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as NotesApplication)
