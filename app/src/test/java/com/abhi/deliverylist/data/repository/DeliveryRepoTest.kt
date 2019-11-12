package com.abhi.deliverylist.data.repository

import androidx.paging.DataSource
import com.abhi.deliverylist.data.repository.DeliveryRepo
import com.abhi.deliverylist.data.local.dataSource.DeliveryDataSource
import com.abhi.deliverylist.networking.DeliveriesApi
import com.abhi.deliverylist.room.entity.DeliveryItem
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import org.junit.Before


class DeliveryRepoTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    //A JUnit Test Rule that swaps the background executor used by the
    // Architecture Components with a different one which executes each task synchronously.

    @MockK
    lateinit var dataSource : DeliveryDataSource
    @MockK
    lateinit var service: DeliveriesApi

    @Before
    @Throws(Exception::class)
    fun setUp(){
        MockKAnnotations.init(this)
        dataSource =  mockk<DeliveryDataSource>()
        service = mockk<DeliveriesApi>()

    }

    @Test
    fun testNotNull(){
        Assert.assertThat(service, CoreMatchers.notNullValue())
        Assert.assertThat(dataSource, CoreMatchers.notNullValue())

    }

    @Test
    fun testGetData(){
        val repo = DeliveryRepo(service = service,dataSource = dataSource)
        Assert.assertThat(repo, CoreMatchers.notNullValue())
        val factory: DataSource.Factory<Int, DeliveryItem> = mockk()
        every {repo.get()}.returns(factory)
        var resultItem = repo.get()
        Assert.assertTrue(resultItem == factory)

    }


    @Test
    fun testSaveData(){
        val item = createDeliveryItem()
        val repo = DeliveryRepo(service = service, dataSource = dataSource)
        Assert.assertThat(repo, CoreMatchers.notNullValue())
        val singleItem: Single<DeliveryItem> = mockk()

        every { repo.save(item) } answers {doNothing()}
        every{repo.getById(any())} answers  {singleItem}
        var resultItem = repo.getById(item.id)
        Assert.assertTrue(resultItem == singleItem)


    }

    private fun doNothing() {}

    private fun createDeliveryItem(): DeliveryItem {
        return DeliveryItem(
            id = 1,  description = "Deliver to test",
            imageUrl = "http://www.google.com",
            address = "Gurgaon India",
            lat = 28.4595,
            lng = 77.0266)
    }

}