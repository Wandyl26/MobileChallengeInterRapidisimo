package com.example.intercommerceapp.presentation.cart

import com.example.intercommerceapp.domain.model.CartItem
import com.example.intercommerceapp.domain.usecase.cart.CartTotals

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val totals: CartTotals = CartTotals(0.0, 0.0, 0.0, 0),
    val isLoading: Boolean = true
)