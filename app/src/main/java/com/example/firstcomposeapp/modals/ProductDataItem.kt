package com.example.firstcomposeapp.modals

import android.os.Parcel
import android.os.Parcelable

data class ProductDataItem(
    val category: String?,
    val description: String?,
    val id: String?,
    val image: String?,
    val price: String?,
    val rating: String?,
    val title: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(category)
        parcel.writeString(description)
        parcel.writeString(id)
        parcel.writeString(image)
        parcel.writeString(price)
        parcel.writeString(rating)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductDataItem> {
        override fun createFromParcel(parcel: Parcel): ProductDataItem {
            return ProductDataItem(parcel)
        }

        override fun newArray(size: Int): Array<ProductDataItem?> {
            return arrayOfNulls(size)
        }
    }
}