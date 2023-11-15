package com.example.tranquiljot.data

import android.content.Context

interface AppContainer {
    val notesRepository: NotesRepository
}
class DefaultAppContainer(
    private val context: Context
): AppContainer {

    override val notesRepository: NotesRepository by lazy {
        OfflineNotesRepository(NotesDatabase.getDatabase(context).notesDao())
    }

}