package com.abhi.deliverylist.di.module


import com.abhi.deliverylist.data.repository.DeliveryRepo
import com.abhi.deliverylist.data.dao.DeliveryDao
import com.abhi.deliverylist.data.local.dataSource.DeliveryDataSource
import com.abhi.deliverylist.domain.datasource.BaseDataSource
import com.abhi.deliverylist.room.entity.DeliveryItem
import com.abhi.deliverylist.domain.repository.BaseRepo
import com.abhi.deliverylist.networking.DeliveriesApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepoModule {

    @Provides
    @Singleton
    fun provideToDoDataSource(
        dao: DeliveryDao
    ): BaseDataSource<DeliveryItem, Int> {
        return DeliveryDataSource(dao)
    }

    @Provides
    @Singleton
    fun provideToDoRepository(
        service: DeliveriesApi,
       dataSource: DeliveryDataSource
    ): BaseRepo<DeliveryItem, Int> {
        return DeliveryRepo(service,  dataSource)
    }
}