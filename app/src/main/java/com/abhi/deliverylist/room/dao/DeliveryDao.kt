package com.abhi.deliverylist.data.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhi.deliverylist.room.entity.DeliveryItem
import io.reactivex.Single

@Dao
interface DeliveryDao {

    /* Method to insert the movies fetched from api
     * to room */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDeliveries(movies: List<DeliveryItem>): LongArray

    /* Method to fetch the movies stored locally */
  //  @Query("SELECT * FROM delivery_item")
   // fun getDeliveriesByPage(): List<DeliveryEntity>


    @Query("SELECT * FROM delivery_item ORDER BY id ASC")
    fun getDeliveryList():  DataSource.Factory<Int, DeliveryItem>

    @Query("SELECT * FROM delivery_item WHERE id= :id")
    fun getDeliveryItem(id: Int): Single<DeliveryItem>

    @Query("SELECT count(*) FROM delivery_item")
    fun getCount(): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDeliveryItem(DeliveryItem : DeliveryItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(DeliveryItemList: List<DeliveryItem>)

    @Query("DELETE FROM delivery_item WHERE id= :id")
    fun deleteDelivery(id: Int)

    @Query("DELETE FROM delivery_item")
    fun deleteAll()
}