package com.abhi.deliverylist.room.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "delivery_item",
    indices  = [Index(value = ["id"], unique = true)])
data class DeliveryItem(
     @PrimaryKey
     val id : Int,
     val description : String?,
     val imageUrl : String?,
     val lat: Double,
     val lng: Double,
     val address: String?
): Parcelable {
     constructor(parcel: Parcel) : this(
          parcel.readInt(),
          parcel.readString(),
          parcel.readString(),
          parcel.readDouble(),
          parcel.readDouble(),
          parcel.readString()
     ) {
     }

     override fun writeToParcel(parcel: Parcel, flags: Int) {
          parcel.writeInt(id)
          parcel.writeString(description)
          parcel.writeString(imageUrl)
          parcel.writeDouble(lat)
          parcel.writeDouble(lng)
          parcel.writeString(address)
     }

     override fun describeContents(): Int {
          return 0
     }

     companion object CREATOR : Parcelable.Creator<DeliveryItem> {
          override fun createFromParcel(parcel: Parcel): DeliveryItem {
               return DeliveryItem(parcel)
          }

          override fun newArray(size: Int): Array<DeliveryItem?> {
               return arrayOfNulls(size)
          }
     }
}

