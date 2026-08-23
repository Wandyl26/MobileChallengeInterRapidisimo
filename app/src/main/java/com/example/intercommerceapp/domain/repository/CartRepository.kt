package com.example.intercommerceapp.domain.repository

import com.example.intercommerceapp.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun addToCart(cartItem: CartItem)
    suspend fun updateQuantity(productId: Int, quantity: Int)
    suspend fun removeFromCart(productId: Int)
}