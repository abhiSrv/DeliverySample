package com.abhi.deliverylist.domain.usecase


import androidx.paging.DataSource
import io.reactivex.Observable
import io.reactivex.Single
import com.abhi.deliverylist.data.Result

interface BaseUseCase<T, K> {

    fun fetchItemList(offset: Int, limit: Int): Observable<Result>

    fun getItemList(): DataSource.Factory<K, T>

    fun getSingleItem(id: K): Single<T>

    fun getCount(): Single<Int>

    fun removeAll()
}