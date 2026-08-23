package com.example.intercommerceapp.presentation.productdetail

import com.example.intercommerceapp.domain.Product


data class ProductDetailUiState(
    val product: Product? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val addedToCart: Boolean = false
)