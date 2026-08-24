package com.example.intercommerceapp.domain.usecase

import com.example.intercommerceapp.domain.model.CartItem
import com.example.intercommerceapp.domain.usecase.cart.CalculateCartTotalsUseCase
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class CalculateCartTotalsUseCaseTest {

    private lateinit var useCase: CalculateCartTotalsUseCase

    @Before
    fun setUp() {
        useCase = CalculateCartTotalsUseCase()
    }

    @Test
    fun `given empty cart returns zero totals`() {
        val result = useCase(emptyList())
        assertThat(result.subtotal).isEqualTo(0.0)
        assertThat(result.tax).isEqualTo(0.0)
        assertThat(result.total).isEqualTo(0.0)
        assertThat(result.itemCount).isEqualTo(0)
    }

    @Test
    fun `given items calculates subtotal tax and total correctly`() {
        val items = listOf(
            CartItem(productId = 1, title = "Product A", price = 100.0, thumbnail = "img", quantity = 2),
            CartItem(productId = 2, title = "Product B", price = 50.0, thumbnail = "img", quantity = 1)
        )
        val result = useCase(items, taxRate = 0.19)
        assertThat(result.subtotal).isEqualTo(250.0)
        assertThat(result.tax).isEqualTo(47.5)
        assertThat(result.total).isEqualTo(297.5)
        assertThat(result.itemCount).isEqualTo(3)
    }

    @Test
    fun `given item with zero quantity is ignored`() {
        val items = listOf(
            CartItem(productId = 1, title = "Product A", price = 100.0, thumbnail = "img", quantity = 0),
            CartItem(productId = 2, title = "Product B", price = 50.0, thumbnail = "img", quantity = 1)
        )
        val result = useCase(items)
        assertThat(result.subtotal).isEqualTo(50.0)
        assertThat(result.itemCount).isEqualTo(1)
    }
}