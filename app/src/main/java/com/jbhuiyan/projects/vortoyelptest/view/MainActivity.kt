package com.jbhuiyan.projects.vortoyelptest.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jbhu.BusinessListFragment
import com.jbhuiyan.projects.vortoyelptest.R
import com.jbhuiyan.projects.vortoyelptest.util.replaceFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(BusinessListFragment(),false)
    }
}