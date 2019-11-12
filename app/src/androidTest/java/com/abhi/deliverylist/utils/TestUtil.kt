package com.abhi.deliverylist.utils

import androidx.test.platform.app.InstrumentationRegistry
import com.abhi.deliverylist.room.entity.DeliveryItem
import com.squareup.okhttp.mockwebserver.MockResponse

object TestUtil {

    fun createDeliveryItem(): DeliveryItem {

        return DeliveryItem(
            id = 1,  description = "Deliver to test",
            imageUrl = "http://www.google.com",
            address = "Gurgaon India",
            lat = 28.4595,
            lng = 77.0266)
    }

    fun createDeliveryItemList(): List<DeliveryItem> {
        val list = ArrayList<DeliveryItem>()
        for (i in 0 until 10) {
            list.add(DeliveryItem(
                id = 1,  description = "Deliver to test",
                imageUrl = "http://www.google.com",
                address = "Gurgaon India",
                lat = 28.4595,
                lng = 77.0266))
        }
        return list
    }

    const val MOCK_PORT: Int = 8888

    private fun loadJson(path: String): String {
        val context = InstrumentationRegistry.getInstrumentation().context
        val stream = context.resources.assets.open(path)
        val reader = stream.bufferedReader()
        val stringBuilder = StringBuilder()
        reader.lines().forEach {
            stringBuilder.append(it)
        }
        return stringBuilder.toString()
    }

    fun createResponse(path: String): MockResponse = MockResponse()
        .setResponseCode(200)
        .setBody(loadJson(path))
}