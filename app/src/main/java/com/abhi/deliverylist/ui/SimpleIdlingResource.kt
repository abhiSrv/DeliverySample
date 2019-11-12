package com.abhi.deliverylist.ui

import androidx.test.espresso.IdlingResource


class SimpleIdlingResource(private var name: String) : IdlingResource {
    private var resourceCallback: IdlingResource.ResourceCallback? = null
    private var counter: Int = 0

    override fun getName() = name

    override fun isIdleNow() = counter <= 0

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = callback!!
    }

    fun incrementCounter() = counter++

    fun decrementCounter() {
        counter--

        if (counter == 0) {
            resourceCallback?.onTransitionToIdle()
        }
    }
}