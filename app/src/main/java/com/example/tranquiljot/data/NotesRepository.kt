package com.example.tranquiljot.data

interface NotesRepository {

}
class OfflineNotesRepository(
    private val notesDao: NotesDao
): NotesRepository {

}