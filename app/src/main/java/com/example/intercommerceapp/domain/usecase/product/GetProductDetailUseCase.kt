package com.example.intercommerceapp.domain.usecase.product

import com.example.intercommerceapp.domain.Product
import com.example.intercommerceapp.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: Int): Product? {
        val local = repository.getProductDetail(id)
        if (local != null) return local

        val result = repository.fetchAndStoreProductDetail(id)
        return result.getOrNull()
    }
}