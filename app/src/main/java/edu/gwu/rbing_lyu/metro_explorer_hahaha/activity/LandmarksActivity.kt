package edu.gwu.rbing_lyu.metro_explorer_hahaha.activity

import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ProgressBar
import edu.gwu.rbing_lyu.metro_explorer_hahaha.R
import edu.gwu.rbing_lyu.metro_explorer_hahaha.utils.LocationDetector
import kotlinx.android.synthetic.main.activity_landmarks.*


class LandmarksActivity : AppCompatActivity(), LocationDetector.LocationListener {
    private val TAG = "LandmarksActivity"

    // Helper declarations
    private lateinit var locationDetector: LocationDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmarks)

        // Get the type passed
        val type = intent.getCharSequenceExtra("type")
        landmark_title.text = type

        // If type is 'nearest', set the appropriate title and attempt to get location
        if (type == "nearest") {
            landmark_title.setText(R.string.nearest_button_text)

            // Initialize location detector
            locationDetector = LocationDetector(this)
            locationDetector.locationListener = this

            // Show the progress bar
            showLoading(true)

            // Attempt to get current location
            locationDetector.detectLocation()
        }

        // Else type is 'favorites', set the appropriate title
        else {
            landmark_title.setText(R.string.favorites_button_text)
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