package com.example.NoteApp.ui


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.NoteApp.ViewModel
import com.example.NoteApp.R
import com.example.NoteApp.db.Note
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {
    val TAG = "MAINFragment"
    var passedNote: Note? = null
    lateinit var userModel:ViewModel
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
         userModel = ViewModelProvider(this).get(ViewModel::class.java)
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
                    userModel.addNote(note)
                    activity?.toast("Note Saved")
                }
                else{
                    note.id = passedNote!!.id
                    userModel.updateNote(note)
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
                userModel.deleteNote(passedNote!!)

                val action = MainFragmentDirections.actionSaveNote()
                Navigation.findNavController(view!!).navigate(action)
            }
            setNegativeButton("No"){_,_ ->}
        }.create().show()
    }

}
