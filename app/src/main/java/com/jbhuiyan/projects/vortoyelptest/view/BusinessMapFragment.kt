package com.jbhuiyan.projects.vortoyelptest.view

import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jbhuiyan.projects.vortoyelptest.R
import com.jbhuiyan.projects.vortoyelptest.businesslogic.Business
import com.jbhuiyan.projects.vortoyelptest.businesslogic.getBusinessString
import com.jbhuiyan.projects.vortoyelptest.util.CustomInfoWindowAdapter
import com.jbhuiyan.projects.vortoyelptest.util.LocationChangedListener
import com.jbhuiyan.projects.vortoyelptest.util.getCurrentLocation


class BusinessMapFragment(val business:Business) : Fragment(R.layout.fragment_business_map) ,
    LocationChangedListener {
    private lateinit var googleMap: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap=googleMap
        this.googleMap.uiSettings.isZoomControlsEnabled=true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onResume() {
        super.onResume()
        getCurrentLocation()
    }

    override fun onChanged(location: Location) {
        //Place current location marker
        val (latLng, markerOptions) = setCurrentLocationMarker(location)
        googleMap.addMarker(markerOptions)
        //Place business location marker
        val businessMarkerOptions = setBusinessMarker()
        googleMap.addMarker(businessMarkerOptions)
        googleMap.setInfoWindowAdapter( CustomInfoWindowAdapter(context!!));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

    private fun setBusinessMarker(): MarkerOptions {
        val businessLatLng = LatLng(business.coordinates.latitude, business.coordinates.longitude)
        val businessMarkerOptions = MarkerOptions()
        businessMarkerOptions.position(businessLatLng)
        businessMarkerOptions.title(business.name)
        businessMarkerOptions.snippet(business.getBusinessString())
        businessMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
        return businessMarkerOptions
    }

    private fun setCurrentLocationMarker(location: Location): Pair<LatLng, MarkerOptions> {
        val latLng = LatLng(location!!.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("I'm here")
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.iamhere))
        return Pair(latLng, markerOptions)
    }
}