package com.example.tranquiljot.ui.screens.entryScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tranquiljot.data.NotesRepository
import com.example.tranquiljot.model.Notes

class EntryScreenViewModel(
    private val notesRepository: NotesRepository,
): ViewModel() {

    var noteUiState by mutableStateOf(NoteUiState())
        private set

    fun updateUiState(noteDetails: NoteDetails) {
        noteUiState = NoteUiState(
            noteDetails = noteDetails,
            isValid = validateInput(noteDetails)
        )
    }
    private fun validateInput(uiState: NoteDetails = noteUiState.noteDetails) : Boolean {
        return with(uiState) {
            title.isNotBlank() && note.isNotBlank()
        }
    }

    suspend fun saveNote() {
        if (validateInput()) {
            notesRepository.insertNotes(noteUiState.noteDetails.toNotes())
        }
    }
}

data class NoteUiState (
    val noteDetails: NoteDetails = NoteDetails(),
    val isValid: Boolean = false,
)
data class NoteDetails(
    val id: Int = 0,
    val title:  String = "",
    val note: String = "",
    val time: String = getTime(),
)
fun NoteDetails.toNotes() : Notes {
    return Notes(
        id = id,
        title = title,
        note = note,
        time = time
    )
}
fun getTime() : String {
    val currentDateTime = java.util.Calendar.getInstance().time
    return currentDateTime.toString()
}