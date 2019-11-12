package com.abhi.deliverylist.ui

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnHolderItem
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToHolder
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.abhi.deliverylist.R
import com.abhi.deliverylist.room.AppDatabase
import com.abhi.deliverylist.utils.TestUtil
import com.abhi.deliverylist.utlis.CustomViewMatchers
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var deliveryDb: AppDatabase


    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, true)


    private lateinit var mockServer: MockWebServer

    @Before
    fun setUp() {

        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())

        deliveryDb = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation()
                .context, AppDatabase::class.java
        ).build()
        deliveryDb.deliveryDao().deleteAll()

        mockServer = MockWebServer()
        mockServer.play(TestUtil.MOCK_PORT)


    }

    @Test
    fun mockApiFailTest() {
        mockServer.enqueue(MockResponse().setResponseCode(400).setBody("Internal server error"))

        assert(activityRule.activity.adapter.itemCount == 0)

        Espresso.onView(ViewMatchers.withText(activityRule.activity.getString(R.string.list_error_message)))
            .withFailureHandler { _, _ -> }.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun viewTest() {
        initTest()

        Espresso.onView(ViewMatchers.withId(R.id.pb_frag_load_more_progress))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
    }

    @Test
    fun clickTest() {
        initTest()

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                ViewActions.click()
            ))

    }


    private fun initTest() {
        mockServer.enqueue(TestUtil.createResponse("json/sample_data.json"))

        activityRule.launchActivity(Intent())
        Espresso.onView(ViewMatchers.withText("OK"))
            .withFailureHandler { _, _ -> }.check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(
                ViewActions.click()
            )
    }

    @After
    fun tearUp() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
        mockServer.shutdown()

    }
    /*@Test
    fun pullToRefresh_shouldPass() {
        initTest()
        onView(withId(R.id.swipe_refresh)).perform(swipeDown())
    }
    */
}