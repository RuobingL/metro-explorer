package edu.gwu.rbing_lyu.metro_explorer_hahaha.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import edu.gwu.rbing_lyu.metro_explorer_hahaha.R
import edu.gwu.rbing_lyu.metro_explorer_hahaha.model.MetroStation
import edu.gwu.rbing_lyu.metro_explorer_hahaha.utils.FetchMetroStationsManager


class MetroStationActivity : AppCompatActivity(), FetchMetroStationsManager.FetchMetroStationsListener{
    private val TAG = "MetroStationActivity"

    // Helper declarations
    private lateinit var fetchMetroStationsManager: FetchMetroStationsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_station)

        fetchMetroStationsManager = FetchMetroStationsManager(this)
        fetchMetroStationsManager.fetchMetroStationsListener = this

        fetchMetroStationsManager.listStations()
    }

    override fun stationsLoaded(stations: List<MetroStation>) {
        // Debug log for now, verify correctly receives list of MetroStation objects parsed
        // from WMATA API
        Log.d(TAG, stations.toString())
    }

    override fun stationsNotLoaded() {

    }
}