package com.example.intercommerceapp.domain.usecase.cart

import com.example.intercommerceapp.domain.model.CartItem
import com.example.intercommerceapp.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(): Flow<List<CartItem>> = cartRepository.getCartItems()
}