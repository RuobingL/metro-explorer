package edu.gwu.rbing_lyu.metro_explorer_hahaha.utils

import android.content.Context
import android.util.Log
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import edu.gwu.rbing_lyu.metro_explorer_hahaha.Constants
import edu.gwu.rbing_lyu.metro_explorer_hahaha.model.MetroStation


class FetchMetroStationsManager(val context: Context) {
    private val TAG = "FetchMetroStationsMgr"
    var fetchMetroStationsListener: FetchMetroStationsListener? = null

    // Define interface to handle WMATA station fetch callbacks
    interface FetchMetroStationsListener {
        fun stationsLoaded(stations: List<MetroStation>)
        fun stationsNotLoaded()
    }

    fun listStations() {
        // Make HTTP request at WMATA API through Ion
        Ion.with(context).load(Constants.WMATA_GET_STATIONS_URL)
                .addHeader("api_key", Constants.WMATA_API_KEY)
                .asJsonObject()
                .setCallback(FutureCallback { error, result ->
                    // Error retrieving station list
                    error?.let {
                        Log.e(TAG, it.toString())
                        // WMATA request for metro stations failed, notify listener
                        fetchMetroStationsListener?.stationsNotLoaded()
                    }

                    // Successfully retrieved station list
                    result?.let {
                        // Parse result into array of MetroStation objects to return
                        // to activity
                        fetchMetroStationsListener?.stationsLoaded(
                            it.getAsJsonArray("Stations").map {
                                station -> MetroStation(
                                    station.asJsonObject["Name"].toString().replace("\"", ""),
                                    station.asJsonObject["Lat"].asFloat,
                                    station.asJsonObject["Lon"].asFloat
                                )
                            }
                        )
                    }
                })
    }
}