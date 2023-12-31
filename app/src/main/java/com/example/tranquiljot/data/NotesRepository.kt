package com.example.tranquiljot.data

import com.example.tranquiljot.model.Notes
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getAllNotes() : Flow<List<Notes>>
    suspend fun insertNotes(notes: Notes)

    suspend fun updateNotes(notes: Notes)

    suspend fun deleteNotes(id: Int)

    fun getNotesById(id: Int) : Flow<Notes>
}
class OfflineNotesRepository(
    private val notesDao: NotesDao
): NotesRepository {
    override fun getAllNotes(): Flow<List<Notes>> = notesDao.getAllNotes()

    override suspend fun insertNotes(notes: Notes) = notesDao.insertNotes(notes)

    override suspend fun updateNotes(notes: Notes) = notesDao.updateNotes(notes)

    override suspend fun deleteNotes(id: Int) = notesDao.deleteNote(id)
    override fun getNotesById(id: Int): Flow<Notes> = notesDao.getNotesById(id)

}