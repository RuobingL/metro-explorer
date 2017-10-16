package edu.gwu.rbing_lyu.metro_explorer_hahaha.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import edu.gwu.rbing_lyu.metro_explorer_hahaha.R
import edu.gwu.rbing_lyu.metro_explorer_hahaha.activity.MetroStationActivity
import edu.gwu.rbing_lyu.metro_explorer_hahaha.model.MetroStation
import edu.gwu.rbing_lyu.metro_explorer_hahaha.activity.LandmarkDetailActivity
import edu.gwu.rbing_lyu.metro_explorer_hahaha.activity.MenuActivity


class MetroStationsAdapter(private val stations: List<MetroStation>) : RecyclerView.Adapter<MetroStationsAdapter.ViewHolder>() {
    private val TAG = "MetroStationsAdapter"

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        // Obtain station at position
        val station = stations?.get(position)

        // Bind station to view holder
        station?.let {
            (holder as ViewHolder).bind(station)
        }
    }

    // Function to retrieve a station by ID
    fun getItem(position: Int): MetroStation {
        return stations[position]
    }

    // Set up interface to be able to set OnClick events for items (stations)
    var onItemClickedListener: OnItemClickedListener? = null

    interface OnItemClickedListener {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return stations.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)

        // Inflate our station row layout
        return ViewHolder(layoutInflater.inflate(R.layout.row_metro_station, parent, false))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val stationNameView: TextView = view.findViewById(R.id.stationName)

        // Update metro station row UI with station name
        fun bind(station: MetroStation) {
            stationNameView.text = station.name.replace("\"", "")
            // And set the onClickListener
            stationNameView.setOnClickListener(this)
        }

        // Specify onClick to be passed to the interfaced function
        override fun onClick(p0: View?) {
            onItemClickedListener?.onItemClick(adapterPosition)
        }
    }
}