package com.example.intercommerceapp.domain.usecase.cart

import com.example.intercommerceapp.domain.repository.CartRepository
import javax.inject.Inject

class UpdateCartItemQuantityUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: Int, quantity: Int) {
        cartRepository.updateQuantity(productId, quantity)
    }
}