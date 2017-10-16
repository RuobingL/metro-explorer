package edu.gwu.rbing_lyu.metro_explorer_hahaha.utils

import android.content.Context
import android.util.Log
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import edu.gwu.rbing_lyu.metro_explorer_hahaha.Constants
import edu.gwu.rbing_lyu.metro_explorer_hahaha.model.Landmark


class YelpAuthManager(val context: Context) {
    private val TAG = "YelpAuthManager"
    var yelpAuthManagerListner: YelpAuthManagerListner? = null

    // Define interface to handle landmark fetch callbacks
    interface YelpAuthManagerListner {
        fun landmarksLoaded(landmarks: List<Landmark>)
        fun landmarksNotLoaded()
    }

    // Gets nearby landmarks using the Yelp API. Takes latitude and longitude
    // of location.
    fun getNearbyLandmarks(latitude: Float, longitude: Float) {
        // Make HTTP request at Yelp API through Ion
        Ion.with(context).load(Constants.YELP_SEARCH_URL)
                .addHeader("Authorization", "Bearer " + Constants.YELP_ACCESS_TOKEN)
                .addQuery("longitude", longitude.toString())
                .addQuery("latitude", latitude.toString())
                .addQuery("categories", "landmarks")
                .addQuery("sort_by", "distance")
                .addQuery("limit", "10")
                .asJsonObject()
                .setCallback(FutureCallback { error, result ->
                    // Error retrieving landmarks list
                    error?.let {
                        Log.e(TAG, it.toString())
                        // Yelp request for nearby landmarks failed, notify listener
                        yelpAuthManagerListner?.landmarksNotLoaded()
                    }

                    // Successfully retrieved nearby landmarks
                    result?.let {
                        // Parse result into array of Landmark objects to return
                        // to activity
                        yelpAuthManagerListner?.landmarksLoaded(
                                it.getAsJsonArray("businesses").map {
                                    landmark -> Landmark(
                                        landmark.asJsonObject["name"].asString,
                                        landmark.asJsonObject["coordinates"].asJsonObject["latitude"].asFloat,
                                        landmark.asJsonObject["coordinates"].asJsonObject["longitude"].asFloat,
                                        landmark.asJsonObject["image_url"].asString,
                                        "",
                                        landmark.asJsonObject["url"].asString
                                )
                                }
                        )
                    }
                })
    }
}