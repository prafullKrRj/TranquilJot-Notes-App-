package com.example.tranquiljot.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tranquiljot.model.Notes
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {


    @Query("SELECT * FROM notes ORDER BY timeStamp DESC")
    fun getAllNotes(): Flow<List<Notes>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNotesById(id: Int): Flow<Notes>

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNote(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotes(notes: Notes)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNotes(notes: Notes)

}