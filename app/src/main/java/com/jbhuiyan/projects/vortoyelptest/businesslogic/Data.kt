package com.jbhuiyan.projects.vortoyelptest.businesslogic

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class YelpModel(var businesses: List<Business>) : Parcelable

@Parcelize
data class Business(
    var id: String,
    var alias: String,
    var name: String,
    @SerializedName("image_url")
    var imageUrl: String,
    @SerializedName("is_closed")
    var isClosed: Boolean,
    var url: String,
    @SerializedName("review_count")
    var reviewCount: Int,
    var categories: List<Category>,
    var rating: Double,
    var coordinates: Coordinates,
    var location: Location,
    var phone: String,
    @SerializedName("display_phone")
    var displayPhone: String,
    var distance: Double?
) : Parcelable

fun Business.getBusinessString(): String {
    var sb: StringBuilder = StringBuilder()
    sb.append(location.displayAddress.joinToString(separator = "\n") + "\n")
    sb.append("Distance: " + String.format("%.2f", distance!! / 1000) + " miles\n")
    if (this.displayPhone !=null) {
        sb.append("Phone: " + displayPhone + "\n")
    }
    sb.append("Total Review: " + reviewCount + "|")
    sb.append(rating)
    return sb.toString()
}

@Parcelize
data class Category(var alias: String, var title: String) : Parcelable

@Parcelize
data class Coordinates(var latitude: Double, var longitude: Double) : Parcelable

@Parcelize
data class Location(
    var address1: String,
    var address2: String,
    var address3: String,
    var city: String,
    var zipCode: String,
    var country: String,
    var state: String,
    @SerializedName("display_address")
    var displayAddress: List<String>
) : Parcelable



