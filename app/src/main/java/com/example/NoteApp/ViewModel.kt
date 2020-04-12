package com.example.NoteApp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.NoteApp.db.Note
import com.example.NoteApp.db.NoteDatabase
import kotlinx.coroutines.launch


class ViewModel(application: Application):AndroidViewModel(application) {
    val TAG = "ViewModel"
    private val noteRepo: theRepository
    val liveNote:LiveData<List<Note>>
   // val returnedVal = MutableLiveData<Note>()
    init {
        val getDao = NoteDatabase.getDatabase(application).noteDao()
        noteRepo = theRepository(getDao)
        liveNote = noteRepo.allNotes

    }
    fun addNote(note: Note){
        viewModelScope.launch {
            noteRepo.addNote(note)
        }
    }
    fun updateNote(note: Note){
        viewModelScope.launch {
            noteRepo.updateNote(note)
        }
    }
    fun deleteNote(note: Note){
        viewModelScope.launch {
            noteRepo.deleteNote(note)
        }
    }
   /*   fun addMultipleNotes(listnote: List<Note>){
        viewModelScope.launch {
            noteRepo.addMultipleNotes(listnote)
        }
    }
        fun findbyID(id: Int){
        viewModelScope.launch {
            returnedVal.value = noteRepo.findbyID(id)
            Log.e(TAG,returnedVal.value.toString())
            // successfully returned value using MutableLiveData
        }
    }
*/
}
