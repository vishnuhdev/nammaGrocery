package com.example.firstcomposeapp

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.firstcomposeapp.apiService.roomDataBase.CartDao
import com.example.firstcomposeapp.apiService.roomDataBase.CartTable
import com.example.firstcomposeapp.apiService.roomDataBase.FavoriteDao
import com.example.firstcomposeapp.apiService.roomDataBase.FavoriteTable

@Database(
    entities = [FavoriteTable::class,CartTable::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao?
    abstract fun cartDao(): CartDao?
}
class NammaGroceryDB : RoomDatabase() {
    val favoriteDao: FavoriteDao?
        get() = instance!!.favoriteDao()

    val cartDao: CartDao?
        get() = instance!!.cartDao()

    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(): AppDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    ImpApplication.applicationContext(),
                    AppDatabase::class.java,
                    "Namma Grocery",
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }
}