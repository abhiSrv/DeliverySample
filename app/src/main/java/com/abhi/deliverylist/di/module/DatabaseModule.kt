package com.abhi.deliverylist.di.module

import android.app.Application
import androidx.room.Room
import com.abhi.deliverylist.data.dao.DeliveryDao
import com.abhi.deliverylist.room.AppDatabase


import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    val DATABASE_NAME= "deliveries.db"

    @Singleton
    @Provides
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application, AppDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideDeliveryDao(database: AppDatabase): DeliveryDao {
        return database.deliveryDao()
    }
}