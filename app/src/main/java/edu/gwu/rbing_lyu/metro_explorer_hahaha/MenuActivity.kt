package edu.gwu.rbing_lyu.metro_explorer_hahaha

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
//import edu.gwu.rbing_lyu.metro_explorer_hahaha.NearestActivity
import kotlinx.android.synthetic.main.activity_menu.*
import android.location.LocationManager
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener


class MenuActivity : AppCompatActivity() {
    val PERMISSION_REQUEST_CODE = 1001

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
//        nearest_button.setOnClickListener({ startActivity(Intent(this, NearestActivity::class.java ))})
        //get Your Current Location


    }
/*
    val LOCATION_CODE = 124
    fun checkLocationPermission(){
        if (Build.VERSION.SDK_INT>=23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_CODE)
                return
            }
        }
        getUserLocation()
    }


    fun getUserLocation(){
        var myLocation = MyLocationListener()
        val locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3f,myLocation)
    }
    var myLocation: Location? = null

    inner class MyLocationListener : LocationListener {
        constructor() : super() {
            myLocation = Location("me")
            myLocation!!.longitude = 0.0
            myLocation!!.latitude = 0.0


        }

        override fun onLocationChanged(location: Location?) {
            myLocation = location
        }

        override fun onProviderEnabled(p0: String?) {
        }

        override fun onProviderDisabled(p0: String?) {
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        }

    }
*/

}
