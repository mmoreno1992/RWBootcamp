package com.mmoreno.favmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Entry point of the app
 */
class MainActivity : AppCompatActivity() {

    /**
     * Overriding default method
     * for displaying a custom layout
     * @param savedInstanceState instance of Bundle for storing
     * the state of an activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        //Making use of Jetpack Navigation component
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }
}
