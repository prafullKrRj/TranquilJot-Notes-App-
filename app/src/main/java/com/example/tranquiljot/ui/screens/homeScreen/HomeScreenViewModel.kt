package com.example.tranquiljot.ui.screens.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tranquiljot.data.NotesRepository
import com.example.tranquiljot.model.Notes
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeScreenViewModel(
    private val notesRepository: NotesRepository
): ViewModel() {
    val homeUiState : StateFlow<HomeUiState> =
        notesRepository.getAllNotes().map {
            HomeUiState(it)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            HomeUiState()
        )

    suspend fun deleteNote(id: Int) {
        notesRepository.deleteNotes(id)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }


}
data class HomeUiState (
    val notes: List<Notes> = listOf()
)