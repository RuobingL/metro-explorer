package edu.gwu.rbing_lyu.metro_explorer_hahaha.activity

import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ProgressBar
import edu.gwu.rbing_lyu.metro_explorer_hahaha.R
import edu.gwu.rbing_lyu.metro_explorer_hahaha.model.MetroStation
import edu.gwu.rbing_lyu.metro_explorer_hahaha.utils.LocationDetector
import kotlinx.android.synthetic.main.activity_landmarks.*
import kotlinx.android.synthetic.main.row_metro_station.*


class LandmarksActivity : AppCompatActivity(), LocationDetector.LocationListener {
    private val TAG = "LandmarksActivity"

    // Helper declarations
    private lateinit var locationDetector: LocationDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmarks)

        // Get the type passed
        val type = intent.getCharSequenceExtra("type")

        // If type is 'nearest', set the appropriate title and attempt to get location
        if (type == "nearest") {
            // Initialize location detector
            locationDetector = LocationDetector(this)
            locationDetector.locationListener = this

            // Show the progress bar
            showLoading(true)

            // Attempt to get current location
            locationDetector.detectLocation()
        }

        else if (type == "byStation") {
            // Get the extras passed through intent by MetroStationActivity
            val stationName = intent.getStringExtra("stationName")
            val longitude = intent.getFloatExtra("longitude")
            val latitude = intent.getFloatExtra("latitude")

            // Set the title as the title of the station click in MetroStationActivity
            landmark_title.setText(stationName)

            // TODO Use location of this metro station to find nearest landmarks
        }

        // If type is favorites
        else if (type == "favorites") {
            // Set the appropriate title
            landmark_title.setText(R.string.favorites_button_text)

            // TODO And get the favorites from PersistanceManager
        }

    }

    private fun showLoading(show: Boolean) {
        if(show) {
            progressBar.visibility = ProgressBar.VISIBLE
        }
        else {
            progressBar.visibility = ProgressBar.INVISIBLE
        }
    }

    override fun locationFound(location: Location) {
        showLoading(false)

        // TODO This is where we'll get the nearest landmarks

        // Debugging log of location for now
        Log.d(TAG, "Location is: " + location.latitude + ", " + location.longitude)

        // TODO Use the location to find nearest metro station
    }

    // Called when location not found
    override fun locationNotFound(reason: LocationDetector.FailureReason) {
        showLoading(false)

        // Log issue, either a timeout or permissions issue
        when(reason){
            LocationDetector.FailureReason.TIMEOUT -> Log.d(TAG, "Location timed out")
            LocationDetector.FailureReason.NO_PERMISSION -> Log.d(TAG, "No location permission")
        }
    }
}