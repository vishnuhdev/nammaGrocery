package com.example.firstcomposeapp.apiService

import com.example.firstcomposeapp.modals.ProductData
import retrofit2.Response
import retrofit2.http.GET

interface GetProduct {
    @GET("qW2tD5")
    suspend fun getProductInfo(): Response<ProductData>
}