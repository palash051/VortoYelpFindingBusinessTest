package com.jbhuiyan.projects.vortoyelptest.businesslogic

import com.jbhuiyan.projects.vortoyelptest.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class YelpBusinessService {
    @Inject
    lateinit var api: YelpBusinessApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getBusinesses(term:String, latitude:Double, longitude:Double,radius:Int): Single<YelpModel> {
        return api.getBusinesses( term,latitude,longitude,radius)
    }
}