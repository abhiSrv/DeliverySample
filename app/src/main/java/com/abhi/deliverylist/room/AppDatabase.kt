package com.abhi.deliverylist.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abhi.deliverylist.data.dao.DeliveryDao
import com.abhi.deliverylist.room.entity.DeliveryItem

@Database(entities = [DeliveryItem::class], version = 1, exportSchema = false)
    abstract class AppDatabase : RoomDatabase() {

    abstract fun deliveryDao(): DeliveryDao
}
