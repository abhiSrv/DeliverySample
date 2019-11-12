package com.abhi.deliverylist.domain.entity

import com.abhi.deliverylist.room.entity.DeliveryItem
import org.junit.Assert
import org.junit.Test


class DeliveryItemTest {

    @Test
    fun testDeliveryItem(){

        val item = DeliveryItem(
            id = 1,  description = "Deliver to test",
            imageUrl = "http://www.google.com",
            address = "Gurgaon India",
            lat = 28.4595,
            lng = 77.0266)
        Assert.assertTrue(item.description == "Deliver to test")
        Assert.assertTrue(item.id == 1)
        Assert.assertTrue(item.address == "Gurgaon India")
        Assert.assertTrue(item.imageUrl == "http://www.google.com" )
        Assert.assertTrue(item.lat == 28.4595)
        Assert.assertTrue(item.lng == 77.0266)

    }
}