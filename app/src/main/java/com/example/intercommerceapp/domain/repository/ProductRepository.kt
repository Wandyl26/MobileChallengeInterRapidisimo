package com.example.intercommerceapp.domain.repository

import androidx.paging.PagingData
import com.example.intercommerceapp.domain.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductsPaged(query: String? = null): Flow<PagingData<Product>>
    suspend fun getProductDetail(id: Int): Product?
    suspend fun fetchAndStoreProductDetail(id: Int): Result<Product>
}