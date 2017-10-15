package edu.gwu.rbing_lyu.metro_explorer_hahaha.utils

import android.content.Context
import android.location.Location
import android.support.v4.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import java.util.*
import kotlin.concurrent.timerTask

// TODO Send last known location back on timeout, if available

class LocationDetector(val context: Context) {
    val fusedLocationClient: FusedLocationProviderClient

    init {
        fusedLocationClient = FusedLocationProviderClient(context)
    }

    // Probable failure reasons
    enum class FailureReason {
        TIMEOUT,
        NO_PERMISSION
    }

    var locationListener: LocationListener? = null

    interface LocationListener {
        fun locationFound(location: Location)
        fun locationNotFound(reason: FailureReason)
    }

    fun detectLocation() {
        // Create location request
        val locationRequest = LocationRequest()

        // Set the interval and priority for the location request
        locationRequest.interval = 0L
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        // Check for the appropriate permission
        val permissionResult = ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION);

        // If has correct permission
        if(permissionResult == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            // Initialize timer
            val timer = Timer()

            // Location detector callback
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    // Stop location updates
                    fusedLocationClient.removeLocationUpdates(this)

                    // Cancel timer
                    timer.cancel()

                    // Fire callback with location
                    locationListener?.locationFound(locationResult.locations.first())
                }
            }

            // Start a timer to ensure location detection ends after 10 seconds
            timer.schedule(timerTask {
                // If timer expires, stop location updates and fire callback
                fusedLocationClient?.removeLocationUpdates(locationCallback)
                locationListener?.locationNotFound(FailureReason.TIMEOUT)
            }, 10*1000) //10 seconds

            // Start location updates
            fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, null)
        }
        // If does not have correct permission, send NO_PERMISSION failure back to listener
        else {
            locationListener?.locationNotFound(FailureReason.NO_PERMISSION)
        }
    }
}