package com.example.NoteApp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.NoteApp.R

class MainActivity : AppCompatActivity() {

    val TAG = "MAINActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // this will create a back button in the MainFragment
        val navController = Navigation.findNavController(this,R.id.contain_Fragment)
        NavigationUI.setupActionBarWithNavController(this,navController)
    }
        // by clicking on the back button the following code will navigate back to HomeFragment
    override fun onSupportNavigateUp(): Boolean {
        val goback = NavigationUI.navigateUp(Navigation.findNavController(this,R.id.contain_Fragment),
        null)
        return  goback
    }

}

