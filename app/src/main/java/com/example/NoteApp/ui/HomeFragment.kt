package com.example.NoteApp.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.NoteApp.MainFragViewModel

import com.example.NoteApp.R
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    val TAG = "HomeFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val homeInflator = inflater.inflate(R.layout.fragment_home, container, false)
        return homeInflator
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)

        val usermodel = ViewModelProvider(this).get(MainFragViewModel::class.java)
        usermodel.liveNote.observe(this, Observer {
            Log.e(TAG, usermodel.liveNote.value.toString())
            val list = usermodel.liveNote.value
            list?.let {
                recycler_view.adapter = NotesAdapter(list)
            }
        })

        button_add.setOnClickListener{

            val action = HomeFragmentDirections.actionAddNote()
            Navigation.findNavController(it).navigate(action)
        }

    }

}
