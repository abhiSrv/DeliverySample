package com.abhi.deliverylist.networking.remote

import com.google.gson.annotations.SerializedName


data class DeliveryResponse  (
    @SerializedName("id")
    val id : Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("location")
    val location: Location

)

class Location(
    @SerializedName("lat")

    val lat: Double,
    @SerializedName("lng")
    val lng: Double,

    @SerializedName("address")
    val address: String

)