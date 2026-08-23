package com.example.intercommerceapp.data.repository


import com.example.intercommerceapp.data.local.dao.cart.CartDao
import com.example.intercommerceapp.data.mapper.cart.toDomain
import com.example.intercommerceapp.data.mapper.cart.toEntity
import com.example.intercommerceapp.domain.model.CartItem
import com.example.intercommerceapp.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun getCartItems(): Flow<List<CartItem>> {
        return cartDao.getCartItems().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun addToCart(cartItem: CartItem) {
        val existing = cartDao.getCartItemByProductId(cartItem.productId)
        if (existing != null) {
            cartDao.updateQuantity(cartItem.productId, cartItem.quantity)
        } else {
            cartDao.insertCartItem(cartItem.toEntity())
        }
    }

    override suspend fun updateQuantity(productId: Int, quantity: Int) {
        if (quantity <= 0) {
            cartDao.removeFromCart(productId)
        } else {
            cartDao.updateQuantity(productId, quantity)
        }
    }

    override suspend fun removeFromCart(productId: Int) {
        cartDao.removeFromCart(productId)
    }
}