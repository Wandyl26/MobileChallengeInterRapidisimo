package com.example.intercommerceapp.domain.usecase.cart

import com.example.intercommerceapp.domain.repository.CartRepository
import javax.inject.Inject

class RemoveFromCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: Int) {
        cartRepository.removeFromCart(productId)
    }
}