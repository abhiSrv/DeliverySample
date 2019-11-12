package com.abhi.deliverylist.viewModel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    private val disposables = CompositeDisposable()
    var isLoading = ObservableBoolean(false)
    var isProgress = ObservableBoolean()

    protected fun addDisposable(d: Disposable) {
        disposables.add(d)
    }

    protected fun getDisposable(): CompositeDisposable {
        return disposables
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}