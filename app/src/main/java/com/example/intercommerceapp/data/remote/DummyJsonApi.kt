package com.example.intercommerceapp.data.remote

import com.example.intercommerceapp.data.remote.dto.ProductDto
import com.example.intercommerceapp.data.remote.dto.ProductListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DummyJsonApi {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductListResponseDto

    @GET("products/search")
    suspend fun searchProducts(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductListResponseDto

    @GET("products/{id}")
    suspend fun getProductDetail(@Path("id") id: Int): ProductDto
}