package com.example.firstcomposeapp.apiService

import com.example.firstcomposeapp.apiService.roomDataBase.FavoriteTable

class ProductData : ArrayList<ProductDataItem>()

class FavoriteList(override val size: Int) : List<FavoriteTable> {
    override fun contains(element: FavoriteTable): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<FavoriteTable>): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(index: Int): FavoriteTable {
        TODO("Not yet implemented")
    }

    override fun indexOf(element: FavoriteTable): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<FavoriteTable> {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: FavoriteTable): Int {
        TODO("Not yet implemented")
    }

    override fun listIterator(): ListIterator<FavoriteTable> {
        TODO("Not yet implemented")
    }

    override fun listIterator(index: Int): ListIterator<FavoriteTable> {
        TODO("Not yet implemented")
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<FavoriteTable> {
        TODO("Not yet implemented")
    }
}