package edu.gwu.rbing_lyu.metro_explorer_hahaha.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import edu.gwu.rbing_lyu.metro_explorer_hahaha.utils.PersistanceManager
import edu.gwu.rbing_lyu.metro_explorer_hahaha.R
import kotlinx.android.synthetic.main.activity_menu.*
import org.jetbrains.anko.toast

class MenuActivity : AppCompatActivity() {
    private val TAG = "MenuActivity"
    private val LOCATION_PERMISSION_REQUEST_CODE = 777

    private lateinit var persistanceManager: PersistanceManager


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        persistanceManager = PersistanceManager()

        favorites_button.setOnClickListener {
            val intent = Intent(this@MenuActivity, LandmarksActivity::class.java)
            // Pass the type 'favorites' for LandmarksActivity as intent
            intent.putExtra("type", "favorites")
            startActivity(intent)
        }

        nearest_button.setOnClickListener {
            val intent = Intent(this@MenuActivity, LandmarksActivity::class.java)
            // Pass the type 'nearest' for LandmarksActivity as intent
            intent.putExtra("type", "nearest")
            startActivity(intent)
        }

        select_station_button.setOnClickListener {
            val intent = Intent(this, MetroStationActivity::class.java)
            startActivity(intent)
        }

        requestPermissionsIfNecessary()
    }

    private fun requestPermissionsIfNecessary() {
        // Check for location permission, if not already granted
        val checkSelfPermission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // If user declines location permission, let them know that there will be consequences :)
        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if(grantResults.isNotEmpty() && grantResults.first() != PackageManager.PERMISSION_GRANTED) {
                toast(R.string.permission_declined)
            }
        }
    }
}
