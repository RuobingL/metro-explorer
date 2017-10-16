package edu.gwu.rbing_lyu.metro_explorer_hahaha.activity

import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.ProgressBar
import edu.gwu.rbing_lyu.metro_explorer_hahaha.R
import edu.gwu.rbing_lyu.metro_explorer_hahaha.adapter.LandmarksAdapter
import edu.gwu.rbing_lyu.metro_explorer_hahaha.model.Landmark
import edu.gwu.rbing_lyu.metro_explorer_hahaha.model.MetroStation
import edu.gwu.rbing_lyu.metro_explorer_hahaha.utils.FetchMetroStationsManager
import edu.gwu.rbing_lyu.metro_explorer_hahaha.utils.LocationDetector
import edu.gwu.rbing_lyu.metro_explorer_hahaha.utils.YelpAuthManager
import kotlinx.android.synthetic.main.activity_landmarks.*
import org.jetbrains.anko.toast


class LandmarksActivity : AppCompatActivity(),
        LocationDetector.LocationListener,
        YelpAuthManager.YelpAuthManagerListner,
        LandmarksAdapter.OnItemClickedListener,
        FetchMetroStationsManager.FetchMetroStationsListener {
    private val TAG = "LandmarksActivity"

    // Helper declarations
    private lateinit var locationDetector: LocationDetector
    private lateinit var yelpAuthManager: YelpAuthManager
    private lateinit var landmarksAdapter: LandmarksAdapter
    private lateinit var fetchMetroStationsManager: FetchMetroStationsManager

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
            val longitude = intent.getFloatExtra("longitude", 0f)
            val latitude = intent.getFloatExtra("latitude", 0f)

            // Set the title as the title of the station click in MetroStationActivity
            nearby_station_title.text = stationName

            // Get landmarks nearest to the selected metro station
            loadNearbyLandmarks(latitude, longitude)
        }

        // If type is favorites
        else if (type == "favorites") {
            // Set the appropriate title
            nearby_station_title.setText(R.string.favorites_button_text)

            // TODO And get the favorites from PersistanceManager
        }

    }

    private fun showLoading(show: Boolean) {
        if(show) {
            progressBar.visibility = ProgressBar.VISIBLE
        }
        else {
            progressBar.visibility = ProgressBar.GONE
        }
    }

    // On Landmark item click
    override fun onItemClick(position: Int) {
        // Just a debug logger that logs the name to verify recycler view + clicking works
        Log.d(TAG, landmarksAdapter.getItem(position).name)
    }

    // Uses the YelpAuthManager to load in nearby landmarks by location
    // Not used when this activity is being used for favorites
    private fun loadNearbyLandmarks(latitude: Float, longitude: Float) {
        yelpAuthManager = YelpAuthManager(this)
        yelpAuthManager.yelpAuthManagerListner = this
        yelpAuthManager.getNearbyLandmarks(latitude, longitude)
        showLoading(true)
    }

    override fun landmarksLoaded(landmarks: List<Landmark>) {
        showLoading(false)

        // Use adapter and recycler view handler to fill list of landmarks
        landmarksAdapter = LandmarksAdapter(landmarks)

        landmarks_recycler_view.layoutManager = LinearLayoutManager(this)
        landmarks_recycler_view.adapter = landmarksAdapter

        // Set the onClick listener for the adapter
        landmarksAdapter.onItemClickedListener = this
    }

    override fun landmarksNotLoaded() {
        showLoading(false)
        toast("Error loading landmarks!")
    }

    override fun locationFound(location: Location) {
        showLoading(false)

        // Find the nearest metro station using WMATA api. Will go to callback
        // stationsLoaded if successful, where nearby landmarks will then
        // be requested from Yelp api
        fetchMetroStationsManager = FetchMetroStationsManager(this)
        fetchMetroStationsManager.fetchMetroStationsListener = this
        fetchMetroStationsManager.getClosestStation(location.latitude.toFloat(), location.longitude.toFloat())
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

    // Get nearby Metro station call successful, if there are any,
    // load the nearby landmarks
    override fun stationsLoaded(stations: List<MetroStation>) {
        // Set the title as the title of the closest station found
        nearby_station_title.text = stations[0].name

        // Get landmarks nearest to the closest metro station
        loadNearbyLandmarks(stations[0].latitude, stations[0].longitude)
    }

    override fun stationsNotLoaded() {
        toast("Could not find nearby Metro stations!")
    }
}