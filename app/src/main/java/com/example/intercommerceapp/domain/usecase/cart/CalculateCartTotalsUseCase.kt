package com.example.intercommerceapp.domain.usecase.cart

import com.example.intercommerceapp.domain.model.CartItem
import javax.inject.Inject

data class CartTotals(
    val subtotal: Double,
    val tax: Double,
    val total: Double,
    val itemCount: Int
)

class CalculateCartTotalsUseCase @Inject constructor() {
    operator fun invoke(items: List<CartItem>, taxRate: Double = 0.19): CartTotals {
        val subtotal = items.sumOf { it.price * it.quantity }
        val tax = subtotal * taxRate
        val total = subtotal + tax
        val itemCount = items.sumOf { it.quantity }
        return CartTotals(
            subtotal = subtotal,
            tax = tax,
            total = total,
            itemCount = itemCount
        )
    }
}