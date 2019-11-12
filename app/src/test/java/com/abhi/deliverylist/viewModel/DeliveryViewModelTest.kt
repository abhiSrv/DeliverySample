package com.abhi.deliverylist.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abhi.deliverylist.data.repository.State
import com.abhi.deliverylist.domain.usecase.DeliveryUseCase
import com.abhi.deliverylist.ui.DeliveryCallback
import com.abhi.deliverylist.utils.NetworkUtils
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DeliveryViewModelTest {

    /*
        @get:Rule
        var rxJavaTestHooksResetRule = RxImmediateSchedulerRule()
    */
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var useCase : DeliveryUseCase

    @MockK
    lateinit var networkUtils: NetworkUtils

    @MockK
    lateinit var deliveryCallback: DeliveryCallback

    private lateinit var viewModel: DeliveryViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = mockk<DeliveryUseCase>()
        networkUtils = mockk<NetworkUtils>()
        deliveryCallback = DeliveryCallback(useCase, CompositeDisposable(),networkUtils = networkUtils)
        every {useCase.getItemList() } returns  (mockk())
        viewModel = DeliveryViewModel(useCase = useCase as DeliveryUseCase,networkUtils = networkUtils)
        viewModel.boundaryCallback = deliveryCallback
    }

    @Test
    fun deliveryListTest() {
        every {useCase.getItemList()} returns (mockk())
        every { useCase.removeAll() } answers {nothing}
        viewModel = DeliveryViewModel(useCase = useCase as DeliveryUseCase,networkUtils = networkUtils)

        assert(viewModel.itemList.value?.size != 0)
        viewModel.boundaryCallback.state.value?.let { assert(it == State.DONE) }
    }

    @Test(expected = Exception::class)
    fun apiFailTest() {
        every {useCase.fetchItemList(any(),any())} throws (Exception())
        deliveryCallback.onZeroItemsLoaded()
    }
}