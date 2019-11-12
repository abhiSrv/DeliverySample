package com.abhi.deliverylist.domain.repository


import androidx.paging.DataSource
import io.reactivex.Single

interface BaseRepo<T, K> {
    fun get(offset: Int, limit: Int): Single<List<T>>

    fun get(): DataSource.Factory<K, T>

    fun getById(id: K): Single<T>

    fun save(entity: T)

    fun save(list: List<T>)

    fun update(entity: T)

    fun delete(entity: T)

    fun deleteAll()

    fun count(): Single<Int>
}