package com.abhi.deliverylist.data.repository

import androidx.paging.DataSource
import com.abhi.deliverylist.domain.datasource.BaseDataSource
import com.abhi.deliverylist.room.entity.DeliveryItem
import com.abhi.deliverylist.domain.repository.BaseRepo
import com.abhi.deliverylist.networking.DeliveriesApi
import com.abhi.deliverylist.networking.remote.DeliveryResponse

import io.reactivex.Single


class DeliveryRepo constructor(
    private val service: DeliveriesApi,
    private val dataSource: BaseDataSource<DeliveryItem, Int>
    ) : BaseRepo<DeliveryItem, Int> {

    override fun get(offset: Int, limit: Int): Single<List<DeliveryItem>> {

        //responseList: List<DeliveryResponse> =s ervice.getDeliveryList(offset, limit)
        return service.getDeliveryList(offset, limit)
            .map {
                val list: List<DeliveryItem> = map(it)

                if (offset == 0) {
                    dataSource.deleteAll()
                }
                dataSource.saveEntityList(list)
                return@map list
            }
    }

    fun map(responseList: List<DeliveryResponse>): List<DeliveryItem> {
        return responseList.map { (map(it)) }
    }

    private fun map(response: DeliveryResponse): DeliveryItem {
        return DeliveryItem(
            id = response.id,
            description= response.description,
            imageUrl = response.imageUrl,
            lat= response.location.lat,
            lng= response.location.lng,
            address = response.location.address)
    }

    override fun get(): DataSource.Factory<Int, DeliveryItem> {
        return dataSource.getEntities()
    }

    override fun getById(id: Int): Single<DeliveryItem> {
        return dataSource.getById(id)
    }

    override fun save(entity: DeliveryItem) {
        dataSource.saveEntity(entity)
    }

    override fun save(list: List<DeliveryItem>) {
        dataSource.saveEntityList(list)
    }

    override fun update(entity: DeliveryItem) {
        dataSource.updateEntity(entity)
    }

    override fun delete(entity: DeliveryItem) {
        dataSource.deleteEntity(entity)
    }

    override fun deleteAll() {
        dataSource.deleteAll()
    }

    override fun count(): Single<Int> {
        return dataSource.count()
    }
}