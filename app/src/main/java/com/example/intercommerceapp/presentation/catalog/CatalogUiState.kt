package com.example.intercommerceapp.presentation.catalog

import androidx.paging.PagingData
import com.example.intercommerceapp.domain.Product

data class CatalogUiState(
    val products: PagingData<Product> = PagingData.empty(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = ""
)