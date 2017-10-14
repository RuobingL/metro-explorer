package edu.gwu.rbing_lyu.metro_explorer_hahaha

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
//import edu.gwu.rbing_lyu.metro_explorer_hahaha.NearestActivity
import kotlinx.android.synthetic.main.activity_menu.*
import android.location.LocationManager
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener


class MenuActivity : AppCompatActivity() {

    val PERMISSION_REQUEST_CODE = 1001

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
