package com.abhi.deliverylist.data.local.dataSource

import androidx.paging.DataSource
import com.abhi.deliverylist.data.dao.DeliveryDao
import com.abhi.deliverylist.domain.datasource.BaseDataSource
import com.abhi.deliverylist.room.entity.DeliveryItem
import io.reactivex.Single
import javax.inject.Inject

class DeliveryDataSource  @Inject constructor(private val dao: DeliveryDao) :
    BaseDataSource<DeliveryItem, Int> {

    override fun getEntities(): DataSource.Factory<Int, DeliveryItem> {
        return dao.getDeliveryList()
    }

    override fun getById(id: Int): Single<DeliveryItem> {
        return dao.getDeliveryItem(id)
    }

    override fun saveEntity(entity: DeliveryItem) {
        dao.insertDeliveryItem(entity)
    }

    override fun saveEntityList(list: List<DeliveryItem>) {
        dao.insertList(list)
    }

    override fun updateEntity(entity: DeliveryItem) {
        dao.insertDeliveryItem(entity)
    }

    override fun deleteEntity(entity: DeliveryItem) {
        dao.deleteDelivery(entity.id)
    }

    override fun deleteAll() {
        dao.deleteAll()
    }

    override fun count(): Single<Int> {
        return dao.getCount()
    }
}