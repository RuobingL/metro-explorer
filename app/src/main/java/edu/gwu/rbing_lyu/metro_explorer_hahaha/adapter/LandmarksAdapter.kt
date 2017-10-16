package edu.gwu.rbing_lyu.metro_explorer_hahaha.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import edu.gwu.rbing_lyu.metro_explorer_hahaha.R
import edu.gwu.rbing_lyu.metro_explorer_hahaha.model.Landmark
import edu.gwu.rbing_lyu.metro_explorer_hahaha.model.MetroStation


class LandmarksAdapter(private val landmarks: List<Landmark>) : RecyclerView.Adapter<LandmarksAdapter.ViewHolder>() {
    private val TAG = "LandmarksAdapter"

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        // Obtain landmark at position
        val landmark = landmarks?.get(position)

        // Bind landmark to view holder
        landmark?.let {
            (holder as ViewHolder).bind(landmark)
        }
    }

    // Function to retrieve a landmark by ID
    fun getItem(position: Int): Landmark {
        return landmarks[position]
    }

    // Set up interface to be able to set OnClick events for items (landmarks)
    var onItemClickedListener: OnItemClickedListener? = null

    interface OnItemClickedListener {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return landmarks.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)

        // Inflate our landmark row layout
        return ViewHolder(layoutInflater.inflate(R.layout.row_landmark, parent, false))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val landmarkNameView: TextView = view.findViewById(R.id.landmark_name)

        // Update landmark row UI with landmark name
        fun bind(landmark: Landmark) {
            landmarkNameView.text = landmark.name
            // And set the onClickListener
            landmarkNameView.setOnClickListener(this)
        }

        // Specify onClick to be passed to the interfaced function
        override fun onClick(p0: View?) {
            onItemClickedListener?.onItemClick(adapterPosition)
        }
    }
}