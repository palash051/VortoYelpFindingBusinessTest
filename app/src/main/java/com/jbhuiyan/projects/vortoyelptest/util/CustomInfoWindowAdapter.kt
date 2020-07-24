package com.jbhuiyan.projects.vortoyelptest.util

import android.content.Context
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.jbhuiyan.projects.vortoyelptest.R
import org.jetbrains.anko.layoutInflater

class CustomInfoWindowAdapter(context: Context) : GoogleMap.InfoWindowAdapter {

    private val contents: View = context.layoutInflater.inflate(R.layout.custom_info_window, null)

    override fun getInfoWindow(marker: Marker): View? {
        render(marker, contents)
        return contents
    }

    override fun getInfoContents(marker: Marker): View? {
        render(marker, contents)
        return contents
    }

    private fun render(marker: Marker, view: View) {
        val titleUi = view.findViewById<TextView>(R.id.title)
        titleUi.text = marker.title
        val snippet = view.findViewById<TextView>(R.id.snippet)
        val ratingbar = view.findViewById<RatingBar>(R.id.rating)
        if (marker.snippet != null) {
            val split = marker.snippet.split("|")
            snippet.text = split[0]
            ratingbar.rating = split[1].toFloat()
            snippet.visibility = View.VISIBLE
            ratingbar.visibility = View.VISIBLE
        } else {
            snippet.visibility = View.GONE
            ratingbar.visibility = View.GONE
        }
    }
}