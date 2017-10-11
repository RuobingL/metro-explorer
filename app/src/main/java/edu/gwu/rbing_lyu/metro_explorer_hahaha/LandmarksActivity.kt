package edu.gwu.rbing_lyu.metro_explorer_hahaha

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_landmarks.*

/**
 * Created by ruobinglyu on 10/2/17.
 */
class LandmarksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmarks)

        // Get the type passed
        val type = intent.getCharSequenceExtra("type")
        landmark_title.text = type

    }
}