package com.abhi.deliverylist.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abhi.deliverylist.domain.usecase.DeliveryUseCase
import com.abhi.deliverylist.rx.RxImmediateSchedulerRule
import com.abhi.deliverylist.utils.NetworkUtils
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DeliveryCallbackTest {

    @get:Rule
    var rxJavaTestHooksResetRule = RxImmediateSchedulerRule()
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    //A JUnit Test Rule that swaps the background executor used by the
    // Architecture Components with a different one which executes each task synchronously.

    @MockK
    lateinit var useCase : DeliveryUseCase

    @MockK
    lateinit var networkUtils: NetworkUtils

    @MockK
    lateinit var boundaryCallback: DeliveryCallback

    @Before
    fun setUp(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = mockk<DeliveryUseCase>()
        networkUtils = mockk<NetworkUtils>()
        boundaryCallback= DeliveryCallback(useCase, CompositeDisposable(), networkUtils = networkUtils)
    }

    @Test
    fun itemAtFrontLoadTest() {
        every { networkUtils.isInternetAvailable() } returns true
        every { useCase.fetchItemList(any(),any())} returns (Observable.just(mockk()))

        val spy = spyk(boundaryCallback)
        spy.onZeroItemsLoaded()
        verify(exactly = 1) {spy.fetchFromNetwork(0, 20)}
        verify(exactly = 1) {
            spy.updateState(any()) }
    }


    @Test
    fun refreshItemsTest() {
        every { networkUtils.isInternetAvailable() } returns true
        every { useCase.fetchItemList(any(), any())} returns (Observable.just(mockk()))

        val spy = spyk(boundaryCallback)
        spy.onRefresh()
        assert(spy.totalCount == 0)
        verify(exactly = 1) {spy.onZeroItemsLoaded()}
        verify(exactly = 1) {spy.fetchFromNetwork(0, 20)}
        verify(exactly = 1) {spy.updateState(any())}

    }

}