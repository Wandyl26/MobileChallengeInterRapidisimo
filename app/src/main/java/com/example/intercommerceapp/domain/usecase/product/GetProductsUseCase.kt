package com.example.intercommerceapp.domain.usecase.product

import androidx.paging.PagingData
import com.example.intercommerceapp.domain.Product
import com.example.intercommerceapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(query: String? = null): Flow<PagingData<Product>> {
        return repository.getProductsPaged(query)
    }
}