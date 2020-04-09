package com.example.NoteApp.ui


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.NoteApp.MainFragViewModel
import com.example.NoteApp.R
import com.example.NoteApp.db.Note
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {
    val TAG = "MAINFragment"
    var passedNote: Note? = null
    lateinit var usermodel:MainFragViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        val maininflator =  inflater.inflate(R.layout.fragment_main, container, false)
        return maininflator
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
         usermodel = ViewModelProvider(this).get(MainFragViewModel::class.java)
        // We don't need to observe change in data in this Fragment
        /*  usermodel.liveNote.observe(this, Observer {
            Log.e(TAG, usermodel.liveNote.value.toString())
        })
        usermodel.returnedVal.observe(this, Observer {
            Log.e(TAG, usermodel.returnedVal.value.toString())
        }) */

        arguments?.let {
            passedNote = MainFragmentArgs.fromBundle(it).noteUpdated
            edit_text_title.setText(passedNote?.title)
            edit_text_note.setText(passedNote?.note)
            Log.e(TAG,passedNote.toString())
        }

        button_save.setOnClickListener {
            val noteTitle = edit_text_title.text.toString().trim()
            val noteBody = edit_text_note.text.toString().trim()

            if (noteTitle.isEmpty()){
                edit_text_title.error = "Title Required"
                edit_text_title.requestFocus()
                return@setOnClickListener
            }
            else if (noteBody.isEmpty()){
                edit_text_note.error = "Body Required"
                edit_text_note.requestFocus()
                return@setOnClickListener
            }
            else {
                val note = Note(title = noteTitle, note = noteBody)

                if (passedNote == null){
                    usermodel.addNote(note)
                    activity?.toast("Note Saved")
                }
                else{
                    note.id = passedNote!!.id
                    usermodel.updateNote(note)
                    activity?.toast("Note Updated")
                }

                val action = MainFragmentDirections.actionSaveNote()
                Navigation.findNavController(it).navigate(action)
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete -> if (passedNote!=null) deleteNote() else activity?.toast("Cannot delete unsaved Note")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote(){

        AlertDialog.Builder(context).apply {

            setTitle("Are you Sure?")
            setMessage("You cannot undo this action")
            setPositiveButton("Yes"){_,_ ->
                usermodel.deleteNote(passedNote!!)

                val action = MainFragmentDirections.actionSaveNote()
                Navigation.findNavController(view!!).navigate(action)
            }
            setNegativeButton("No"){_,_ ->}
        }.create().show()
    }

}
