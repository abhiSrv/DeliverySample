package com.abhi.deliverylist.utlis

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import com.abhi.deliverylist.adapters.DeliveryListAdapter
import org.hamcrest.Matcher

object CustomViewMatchers {

    fun withDeliveryDiscription(description: String): Matcher<RecyclerView.ViewHolder> {
        return object :
            BoundedMatcher<RecyclerView.ViewHolder, DeliveryListAdapter.ItemHolder>(
                DeliveryListAdapter.ItemHolder::class.java
            ) {
            override fun matchesSafely(item: DeliveryListAdapter.ItemHolder): Boolean {
                return (item.deliveryItem.id.toString().plus(
                    item.deliveryItem.description
                ) == description)
            }

            override fun describeTo(description: org.hamcrest.Description?) {
                description?.appendText("view holder with description: $description")
            }
        }
    }
}