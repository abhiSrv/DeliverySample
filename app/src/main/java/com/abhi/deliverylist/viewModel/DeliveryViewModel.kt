package com.abhi.deliverylist.viewModel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.abhi.deliverylist.domain.usecase.DeliveryUseCase
import com.abhi.deliverylist.room.entity.DeliveryItem
import com.abhi.deliverylist.ui.DeliveryCallback
import com.abhi.deliverylist.utils.NetworkUtils
import javax.inject.Inject

const val PAGE_SIZE=20
class DeliveryViewModel @Inject constructor(private val useCase: DeliveryUseCase, private val networkUtils: NetworkUtils): BaseViewModel() {

    var itemList: LiveData<PagedList<DeliveryItem>>
    var item = ObservableField<DeliveryItem>()
    var noData = ObservableBoolean(false)
    var boundaryCallback: DeliveryCallback

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
        boundaryCallback =
            DeliveryCallback(useCase, getDisposable(), networkUtils)
        itemList = LivePagedListBuilder(useCase.getItemList(), config)
            .setBoundaryCallback(boundaryCallback).build()
    }

    fun retry() = boundaryCallback.retry()

    fun onRefresh() {
        isLoading.set(true)
        boundaryCallback.onRefresh()

    }

}