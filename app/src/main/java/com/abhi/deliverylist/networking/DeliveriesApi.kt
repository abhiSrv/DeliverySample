package com.abhi.deliverylist.networking


import com.abhi.deliverylist.networking.remote.DeliveryResponse

import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Query

interface DeliveriesApi {

    /*
    Protocol HTTPS
        Hostname mock-api-mobile.dev.lalamove.com
        Method GET
        Endpoint /deliveries
        Query String Parameters- offset
        Description: Starting index.
        Data type: Integer.-  limit
        Description: Number of items requested
        Data type: Integer.

    * */

    @GET("deliveries")
    fun getDeliveryList(@Query("offset") offset: Int, @Query("limit") limit: Int
    ): Single<List<DeliveryResponse>> // <DeliveryModel>>
}

