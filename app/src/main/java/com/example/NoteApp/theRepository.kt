package com.example.NoteApp

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.NoteApp.db.Note
import com.example.NoteApp.db.NoteDao

class theRepository(private val noteDao: NoteDao) {
    val TAG = "theRepository"

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()


    suspend fun addNote(note: Note){
        noteDao.addNote(note)
    }
    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }
    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }
/*  suspend fun addMultipleNotes(notelist: List<Note>){
        noteDao.addMultipleNotes(notelist)
    }
    suspend fun findbyID(id : Int): Note {
        val returneddao = noteDao.findbyID(id)
        Log.e(TAG,returneddao.toString())
        return returneddao
    }
*/
}
