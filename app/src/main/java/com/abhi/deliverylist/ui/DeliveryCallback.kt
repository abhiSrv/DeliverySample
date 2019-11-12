package com.abhi.deliverylist.ui

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.abhi.deliverylist.room.entity.DeliveryItem
import com.abhi.deliverylist.utils.NetworkUtils
import com.abhi.deliverylist.data.repository.State
import com.abhi.deliverylist.domain.usecase.BaseUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import io.reactivex.android.schedulers.AndroidSchedulers
import com.abhi.deliverylist.data.Result

const val lastLimit=20
class DeliveryCallback (private val useCase: BaseUseCase<DeliveryItem, Int>,
                        private var disposable: CompositeDisposable,
                        private val networkUtils: NetworkUtils
       ) : PagedList.BoundaryCallback<DeliveryItem>(){


    var totalCount: Int = 0
    private var isLoaded: Boolean = false
    private var loadingFirstPage: Boolean = false
    var state: MutableLiveData<String> = MutableLiveData()

    override fun onZeroItemsLoaded() {
        loadingFirstPage = true
        fetchFromNetwork(totalCount,lastLimit)
      //  EspressoIdlingResource.increment()

    }

    override fun onItemAtFrontLoaded(itemAtFront:DeliveryItem) {
        super.onItemAtFrontLoaded(itemAtFront)

        disposable.add(useCase.getCount()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                if (totalCount < result) totalCount = result
            })

    }

    override fun onItemAtEndLoaded(itemAtEnd: DeliveryItem) {
        super.onItemAtEndLoaded(itemAtEnd)
        if (isLoaded) {
            loadingFirstPage = totalCount <= 0
            fetchFromNetwork(totalCount,lastLimit)
        }
    }

    fun fetchFromNetwork(offset: Int, limit: Int) {
        if (!networkUtils.isInternetAvailable()){
            updateState(State.NETWORK_ERROR)
            return
        }


        if (offset==0){
            updateState(State.LOADING)
        }else{
            updateState(State.PAGE_LOADING)
        }

        disposable.add(

            useCase.fetchItemList(offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    when (result) {
                        is Result.Success -> {
                            success(result.response as List<DeliveryItem>)
                        }
                        is Result.Failure -> {
                            error(result.throwable)
                        }
                    }
                }, { e -> error(e) })
        )
    }

    private fun success(list: List<DeliveryItem>) {
        if(list.size==0){
            updateState(State.DONE)
        }
        else {
            totalCount += list.size
            isLoaded = true
            updateState(State.LOADED)

        }
       // EspressoIdlingResource.decrement()
    }

    private fun error(throwable: Throwable) {
        if (throwable is UnknownHostException) {
            updateState(State.NETWORK_ERROR)
        } else {
            updateState(State.ERROR)
        }
       // EspressoIdlingResource.decrement()
    }

    fun updateState(state: String) {
        this.state.postValue(state)
    }

    fun retry() {
        loadingFirstPage = true
        fetchFromNetwork(totalCount, lastLimit)
    }

    fun onRefresh() {
        totalCount = 0
        isLoaded = false
        disposable.clear()
        onZeroItemsLoaded()
    }
}
