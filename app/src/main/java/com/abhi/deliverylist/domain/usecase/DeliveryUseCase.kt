package com.abhi.deliverylist.domain.usecase


import androidx.paging.DataSource
import com.abhi.deliverylist.room.entity.DeliveryItem
import com.abhi.deliverylist.domain.repository.BaseRepo
import com.abhi.deliverylist.data.Result
import com.abhi.deliverylist.domain.usecase.BaseUseCase

import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class DeliveryUseCase @Inject constructor(private val repo: BaseRepo<DeliveryItem, Int>):
    BaseUseCase<DeliveryItem, Int> {

    override fun fetchItemList(offset: Int, limit: Int): Observable<Result> {
        return repo.get(offset, limit).toObservable()
            .map { Result.Success(it) as Result }
            .onErrorReturn { Result.Failure(it) }
            .startWith(Result.Loading)
    }

    override fun getItemList(): DataSource.Factory<Int, DeliveryItem> {
        return repo.get()
    }

    override fun getSingleItem(id: Int): Single<DeliveryItem> {
        return repo.getById(id)
    }

    override fun getCount(): Single<Int> {
        return repo.count()
    }

    override fun removeAll() {
        repo.deleteAll()
    }
}