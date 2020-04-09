package com.example.NoteApp.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NoteDao {
    @Insert
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM Note ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

/*  @Insert
    suspend fun addMultipleNotes(listofNotes: List<Note>)

    @Query("SELECT * FROM Note WHERE id LIKE :id")
    suspend fun findbyID(id : Int): Note
*/
}
