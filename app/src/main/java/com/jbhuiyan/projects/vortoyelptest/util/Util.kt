package com.jbhuiyan.projects.vortoyelptest.util


import android.location.Location
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment

import com.jbhuiyan.projects.vortoyelptest.R
import com.jbhuiyan.projects.vortoyelptest.businesslogic.Business

interface ListItemClickListener {
    fun onItemClick(business: Business)
}

interface UpdateLocation {
    fun updateLocationOnChanged(location: Location, isGPS: Boolean)
    fun getLastUpdatedLocation(): Location
}


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


