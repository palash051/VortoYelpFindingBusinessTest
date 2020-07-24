package com.jbhuiyan.projects.vortoyelptest.businesslogic

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface YelpBusinessApi {
    @GET("businesses/search")
    fun getBusinesses(
        @Query("term") term: String?,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Int
    ): Single<YelpModel>
}