package com.abhi.deliverylist.domain.usecase


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.abhi.deliverylist.data.repository.DeliveryRepo
import com.abhi.deliverylist.room.entity.DeliveryItem
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Before

import org.junit.Rule
import org.junit.Test


class DeliveryUseCaseTest{

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repo: DeliveryRepo

    @Before
    fun init(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        repo =  mockk<DeliveryRepo>()
    }

    @Test
    fun testNotNull(){
        assertThat(repo, CoreMatchers.notNullValue())
    }

    @Test
    fun testGetItemList(){
        val useCase = DeliveryUseCase(repo)
        assertThat(useCase, CoreMatchers.notNullValue())
        val factory: DataSource.Factory<Int, DeliveryItem> = mockk()
        every { useCase.getItemList()} returns (factory)
        var resultItem = useCase.getItemList()
        assertTrue(resultItem==factory)
    }

    @Test
    fun testGetSingleItem(){
        val useCase = DeliveryUseCase(repo)
        assertThat(useCase, CoreMatchers.notNullValue())
        val singleItem: Single<DeliveryItem> = mockk()
        every { useCase.getSingleItem(any())} returns (singleItem)
        var resultItem = useCase.getSingleItem(1)
        assertTrue(resultItem==singleItem)
    }

    @Test
    fun testGetCount(){
        val useCase = DeliveryUseCase(repo)
        assertThat(useCase, CoreMatchers.notNullValue())
        val singleItem: Single<Int> = mockk()
        every {useCase.getCount()}returns (singleItem)
        var resultItem = useCase.getCount()
        assertTrue(resultItem==singleItem)
    }
}