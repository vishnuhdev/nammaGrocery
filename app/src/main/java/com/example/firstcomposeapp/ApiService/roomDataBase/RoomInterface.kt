package com.example.firstcomposeapp.apiService.roomDataBase

import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favItems: FavoriteTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun fireBaseInsert(favItems: MutableList<FavoriteTable>)

    @Query("SELECT * FROM favoriteTable")
    suspend fun getAll(): List<FavoriteTable>

    @Delete
    fun delete(favItems: FavoriteTable)

    @Delete
    fun deleteAll(favItems: List<FavoriteTable>)
}

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cartItems: CartTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun fireBaseInsert(favItems: MutableList<CartTable>)

    @Query("SELECT * FROM cartTable")
    suspend fun getAll(): List<CartTable>

    @Query("SELECT * FROM cartTable WHERE UId = :cartTableId")
    fun getSingleItem(cartTableId: String): CartTable

    @Query("UPDATE cartTable SET Count = Count + 1 WHERE UId = :cartTableId")
    fun incrementCount(cartTableId: String)

    @Query("UPDATE cartTable SET finalPrice = ROUND(CAST(REPLACE(price, '$', '') AS REAL) * Count, 2) WHERE UId = :cartTableId")
    fun setPrice(cartTableId: String)

    @Query("UPDATE cartTable SET Count = Count - 1 WHERE UId = :cartTableId")
    fun decrementCount(cartTableId: String)

    @Delete
    fun delete(cartItems: CartTable)

    @Delete
    fun deleteAll(cartItems: List<CartTable>)
}