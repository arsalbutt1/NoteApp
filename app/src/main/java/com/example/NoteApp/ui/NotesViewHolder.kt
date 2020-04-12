package com.example.NoteApp.ui

import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.NoteApp.db.Note
import kotlinx.android.synthetic.main.each_item.view.*

class NotesViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    private val tvTitle = itemView.text_view_title
    private val tvNote = itemView.text_view_note

    fun clickOn(currentItem: Note){

        tvTitle.text = currentItem.title
        tvNote.text = currentItem.note

        itemView.setOnClickListener{

            val action = HomeFragmentDirections.actionAddNote(currentItem)
            Navigation.findNavController(it).navigate(action)

        }

    }
}