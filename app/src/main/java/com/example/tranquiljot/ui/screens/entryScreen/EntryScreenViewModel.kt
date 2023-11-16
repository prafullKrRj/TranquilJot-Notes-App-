package com.example.tranquiljot.ui.screens.entryScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tranquiljot.data.NotesRepository
import com.example.tranquiljot.model.Notes
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
            title.isNotBlank() || note.isNotBlank()
        }
    }

    suspend fun saveNote() {
        noteUiState.noteDetails.timeStamp = System.currentTimeMillis()
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
    var time: String = getTime(),
    var timeStamp: Long = 0
)
fun NoteDetails.toNotes() : Notes {
    return Notes(
        id = id,
        title = title,
        note = note,
        time = time,
        timeStamp = timeStamp
    )
}
fun getTime() : String {
    val time = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    val formattedTime = time.format(formatter)
    return ("${time.dayOfMonth} ${getMonth(time.monthValue)} ${formattedTime.uppercase()}")
}
private fun getMonth(mon: Int) : String {
    return when (mon) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        else -> "December"
    }
}
