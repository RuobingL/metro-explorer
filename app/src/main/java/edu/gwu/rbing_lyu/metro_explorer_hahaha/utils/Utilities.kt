package edu.gwu.rbing_lyu.metro_explorer_hahaha

import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.util.Log

import com.google.gson.JsonObject

import edu.gwu.rbing_lyu.metro_explorer_hahaha.model.Landmark
import edu.gwu.rbing_lyu.metro_explorer_hahaha.model.MetroStation

import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.charset.Charset
import java.util.ArrayList
import java.util.Collections
import java.util.Random

object Utilities {
    private const val TAG = "Utilities"

    fun parseURLFromBingJSON(jsonObject: JsonObject, desiredOrientation: Int): URL? {
        //parse through JSON to obtain an image url that meets criteria: not too large, and correct orientation
        val imageResults = jsonObject.getAsJsonArray("value")
        if (imageResults != null && imageResults.size() > 0) {
            for (i in 0..imageResults.size() - 1) {
                val imageResult = imageResults.get(i).asJsonObject
                val tooBig = Integer.parseInt(imageResult.get("contentSize").asString.replace(" B", "")) > Constants.MAX_IMAGE_FILE_SIZE_IN_BYTES

                if (!tooBig) {
                    val width = imageResult.get("width").asInt
                    val height = imageResult.get("height").asInt

                    if (desiredOrientation == Configuration.ORIENTATION_PORTRAIT) {
                        if (height > width) {
                            return URL(imageResult.get("contentUrl").asString)
                        }
                    } else if (desiredOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                        if (width > height) {
                            return URL(imageResult.get("contentUrl").asString)
                        }
                    }
                }
            }
        }

        Log.e(TAG, "No image results found")
        return null
    }
}