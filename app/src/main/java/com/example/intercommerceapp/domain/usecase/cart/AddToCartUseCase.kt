package com.example.intercommerceapp.domain.usecase.cart

import com.example.intercommerceapp.domain.Product
import com.example.intercommerceapp.domain.model.CartItem
import com.example.intercommerceapp.domain.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(product: Product, quantity: Int = 1) {
        val cartItem = CartItem(
            productId = product.id,
            title = product.title,
            price = product.price,
            thumbnail = product.thumbnail,
            quantity = quantity
        )
        cartRepository.addToCart(cartItem)
    }
}