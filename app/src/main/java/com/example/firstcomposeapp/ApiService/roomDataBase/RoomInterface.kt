package com.example.firstcomposeapp.apiService.roomDataBase

import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favItems : FavoriteTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun fireBaseInsert(favItems: MutableList<FavoriteTable>)

    @Query("SELECT * FROM favoriteTable")
    suspend fun getAll(): List<FavoriteTable>

    @Delete
    fun delete(favItems: FavoriteTable)

    @Delete
    fun deleteAll(favItems: List<FavoriteTable>)
}