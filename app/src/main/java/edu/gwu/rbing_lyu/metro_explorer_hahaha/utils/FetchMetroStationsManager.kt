package edu.gwu.rbing_lyu.metro_explorer_hahaha.utils

import android.content.Context
import android.util.Log
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import edu.gwu.rbing_lyu.metro_explorer_hahaha.Constants
import edu.gwu.rbing_lyu.metro_explorer_hahaha.model.MetroStation
import org.json.JSONArray


class FetchMetroStationsManager(val context: Context) {
    private val TAG = "FetchMetroStationsMgr"
    var fetchMetroStationsListener: FetchMetroStationsListener? = null

    // Define interface to handle WMATA station fetch callbacks
    interface FetchMetroStationsListener {
        fun stationsLoaded(stations: List<MetroStation>)
        fun stationsNotLoaded()
    }

    // Returns complete list of MetroStation objects
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
                                    station.asJsonObject["Name"].asString,
                                    station.asJsonObject["Lat"].asFloat,
                                    station.asJsonObject["Lon"].asFloat
                                )
                            }
                        )
                    }
                })
    }

    // Returns a single MetroStation object representing the closest station
    // to coordinates provided
    fun getClosestStation(latitude: Float, longitude: Float) {
        // Make HTTP request at WMATA API through Ion
        Ion.with(context).load(Constants.WMATA_GET_CLOSEST_STATION_URL)
                .addHeader("api_key", Constants.WMATA_API_KEY)
                .addQuery("Lat", latitude.toString())
                .addQuery("Lon", longitude.toString())
                .addQuery("Radius", "1000")
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
                        // Just get the first one
                        val station = it.getAsJsonArray("Entrances").get(0)

                        // And request its details to get the name
                        Ion.with(context).load(Constants.WMATA_GET_STATION_BY_ID)
                                .addHeader("api_key", Constants.WMATA_API_KEY)
                                .addQuery("StationCode", station.asJsonObject["StationCode1"].asString)
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
                                                listOf(MetroStation(
                                                        it.getAsJsonPrimitive("Name").asString,
                                                        it.getAsJsonPrimitive("Lat").asFloat,
                                                        it.getAsJsonPrimitive("Lon").asFloat
                                                ))
                                        )
                                    }
                                })

                    }
                })
    }
}