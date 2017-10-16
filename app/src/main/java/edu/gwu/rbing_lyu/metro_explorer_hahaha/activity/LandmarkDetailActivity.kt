package edu.gwu.rbing_lyu.metro_explorer_hahaha.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import edu.gwu.rbing_lyu.metro_explorer_hahaha.R
import kotlinx.android.synthetic.main.activity_landmark_detail.*


class LandmarkDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmark_detail)

        // Set content from intent passed
        val title = intent.getCharSequenceExtra("title")
        //val address = intent.getCharSequenceExtra("address")
        //val imageUrl = intent.getCharSequenceExtra("imageUrl")


        landmark_detail_title.text = title
        landmark_detail_name.text = title
        //landmark_detail_address.text = address
        //landmark_detail_image_src.set(imageUrl)
    }
}