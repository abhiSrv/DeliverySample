package com.abhi.deliverylist.room.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abhi.deliverylist.utils.TestUtil
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeliveryDaoTest : AppDatabaseTest(){

    @Test
    fun insertAndReadTest() {

        val deliveryItem = TestUtil.createDeliveryItem()

        db.deliveryDao().deleteAll()

        db.deliveryDao().getCount().test()?.assertValue(0)

        db.deliveryDao().insertDeliveryItem(deliveryItem)

        db.deliveryDao().getCount().test()?.assertValue(1)

        val item = (db.deliveryDao().getDeliveryItem(1))

        MatcherAssert.assertThat(item, CoreMatchers.notNullValue())
        item.test().assertValue(deliveryItem)

        MatcherAssert.assertThat(item.blockingGet(), CoreMatchers.notNullValue())
        MatcherAssert.assertThat(item.blockingGet().description, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(item.blockingGet().id, CoreMatchers.notNullValue())

    }

    @Test
    fun insertAllAndReadTest() {
        val list = TestUtil.createDeliveryItemList()
        db.deliveryDao().deleteAll()

        db.deliveryDao().getCount().test()?.assertValue(0)

        db.deliveryDao().insertList(list)

        db.deliveryDao().getCount().test()?.assertValue(1)

    }
}