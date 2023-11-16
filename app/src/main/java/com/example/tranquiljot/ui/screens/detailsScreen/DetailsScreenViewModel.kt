package com.example.tranquiljot.ui.screens.detailsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tranquiljot.data.NotesRepository
import com.example.tranquiljot.model.Notes
import com.example.tranquiljot.ui.screens.entryScreen.NoteDetails
import com.example.tranquiljot.ui.screens.entryScreen.NoteUiState
import com.example.tranquiljot.ui.screens.entryScreen.toNotes
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailsScreenViewModel(
    private val notesRepository: NotesRepository,
    val id: Int
): ViewModel() {

    var noteUiState by mutableStateOf(NoteUiState())
        private set

    init {
        viewModelScope.launch {
            noteUiState = notesRepository.getNotesById(id).filterNotNull().first().toUiState(true)
        }
    }
    suspend fun updateNote() {
        if (validateInput(noteUiState.noteDetails)) {
            notesRepository.updateNotes(noteUiState.noteDetails.toNotes())
        }
    }
    fun updateUiState(noteDetails: NoteDetails) {
        noteUiState = NoteUiState(
            noteDetails = noteDetails,
            isValid = validateInput(noteDetails)
        )
    }
    private fun validateInput(uiState: NoteDetails = noteUiState.noteDetails) : Boolean {
        return with(uiState) {
            title.isNotBlank() || note.isNotBlank()
        }
    }
}
fun Notes.toUiState(isValid: Boolean) : NoteUiState = NoteUiState(
    noteDetails = this.toNoteDetails(),
    isValid = isValid
)
fun Notes.toNoteDetails() : NoteDetails = NoteDetails(
    id = id,
    title = title,
    note = note,
    time = time
)