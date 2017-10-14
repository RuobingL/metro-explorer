package edu.gwu.rbing_lyu.metro_explorer_hahaha.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import edu.gwu.rbing_lyu.metro_explorer_hahaha.R
//import edu.gwu.rbing_lyu.metro_explorer_hahaha.NearestActivity
import kotlinx.android.synthetic.main.activity_menu.*


class MenuActivity : AppCompatActivity() {

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        favorites_button.setOnClickListener {
            val intent = Intent(this@MenuActivity, LandmarksActivity::class.java)
            intent.putExtra("type", "Favorites")
            startActivity(intent)
        }

        nearest_button.setOnClickListener {
            val intent = Intent(this@MenuActivity, LandmarksActivity::class.java)
            intent.putExtra("type", "Nearest Stations")
            startActivity(intent)
        }

        select_station_button.setOnClickListener {
            val landmarksIntent = Intent(this, LandmarksActivity::class.java)
            startActivity(landmarksIntent)
        }
    }
}
