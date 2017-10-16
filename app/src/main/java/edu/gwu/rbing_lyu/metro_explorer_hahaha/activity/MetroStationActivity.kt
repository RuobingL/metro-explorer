package edu.gwu.rbing_lyu.metro_explorer_hahaha.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import edu.gwu.rbing_lyu.metro_explorer_hahaha.R
import edu.gwu.rbing_lyu.metro_explorer_hahaha.adapter.MetroStationsAdapter
import edu.gwu.rbing_lyu.metro_explorer_hahaha.model.MetroStation
import edu.gwu.rbing_lyu.metro_explorer_hahaha.utils.FetchMetroStationsManager
import kotlinx.android.synthetic.main.activity_select_station.*


class MetroStationActivity : AppCompatActivity(), FetchMetroStationsManager.FetchMetroStationsListener, MetroStationsAdapter.OnItemClickedListener {
    private val TAG = "MetroStationActivity"

    // Helper declarations
    private lateinit var fetchMetroStationsManager: FetchMetroStationsManager
    private lateinit var metroStationsAdapter: MetroStationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_station)

        // Initialize and fetch list of metro stations fro WMATA API
        fetchMetroStationsManager = FetchMetroStationsManager(this)
        fetchMetroStationsManager.fetchMetroStationsListener = this

        fetchMetroStationsManager.listStations()
    }

    override fun onItemClick(position: Int) {
        // Send to LandmarksActivity and pass the station clicked
        val station = metroStationsAdapter.getItem(position)
        val intent = Intent(this@MetroStationActivity, LandmarksActivity::class.java)
        intent.putExtra("type", "byStation")
        intent.putExtra("stationName", station.name)
        intent.putExtra("longitude", station.longitude)
        intent.putExtra("latitude", station.latitude)
        startActivity(intent)
    }

    override fun stationsLoaded(stations: List<MetroStation>) {
        // Use adapter and recycler view handler to fill list of stations
        metroStationsAdapter = MetroStationsAdapter(stations)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = metroStationsAdapter

        // Set the onClick listener for the adapter
        metroStationsAdapter.onItemClickedListener = this
    }

    override fun stationsNotLoaded() {

    }
}