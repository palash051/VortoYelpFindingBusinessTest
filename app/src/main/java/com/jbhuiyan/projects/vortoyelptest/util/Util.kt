package com.jbhuiyan.projects.vortoyelptest.util

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.jbhuiyan.projects.vortoyelptest.R
import com.jbhuiyan.projects.vortoyelptest.businesslogic.Business

fun AppCompatActivity.replaceFragment(fragment: Fragment, needBackStack: Boolean) {
    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(R.id.host, fragment)
    if (needBackStack) {
        transaction.addToBackStack(null)
    }
    transaction.commit()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    replaceFragment(fragment, true)
}

interface LocationChangedListener {
    fun onChanged(location: Location)
}

interface ListItemClickListener{
    fun onItemClick(business: Business)
}

fun Fragment.getCurrentLocation() {
    if (ActivityCompat.checkSelfPermission(
            context!!,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(
            context!!,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                (this as LocationChangedListener).onChanged(location!!);
            }
    }
}

