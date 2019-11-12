package com.abhi.deliverylist.domain.datasource


import androidx.paging.DataSource
import io.reactivex.Single

interface BaseDataSource<T, K> {

    fun getEntities(): DataSource.Factory<K, T>

    fun getById(id: K): Single<T>

    fun saveEntity(entity: T)

    fun saveEntityList(list: List<T>)

    fun updateEntity(entity: T)

    fun deleteEntity(entity: T)

    fun deleteAll()

    fun count(): Single<Int>
}