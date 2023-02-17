package com.example.firstcomposeapp.apiService.roomDataBase

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favoriteTable")
data class FavoriteTable(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "UId") val id: String,
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "price") val price: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "rating") val rating: String?,
){
    constructor():this("","","","","","","")
}

@Entity(tableName = "cartTable")
data class CartTable(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "UId") val id: String,
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "Count") val count: Int?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "price") val price: String?,
    @ColumnInfo(name = "finalPrice") val finalPrice: Double?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "rating") val rating: String?,

){
    constructor():this("","",0,"","",0.0,"","","")
}
