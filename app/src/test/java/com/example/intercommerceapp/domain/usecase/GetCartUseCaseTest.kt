package com.example.intercommerceapp.domain.usecase

import com.example.intercommerceapp.domain.model.CartItem
import com.example.intercommerceapp.domain.repository.CartRepository
import com.example.intercommerceapp.domain.usecase.cart.GetCartUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCartUseCaseTest {

    private lateinit var cartRepository: CartRepository
    private lateinit var useCase: GetCartUseCase

    @Before
    fun setUp() {
        cartRepository = mockk()
        useCase = GetCartUseCase(cartRepository)
    }

    @Test
    fun `invoke returns cart items from repository`() = runBlocking {
        val expectedItems = listOf(
            CartItem(1, "Product A", 10.0, "img", 2),
            CartItem(2, "Product B", 20.0, "img", 1)
        )
        coEvery { cartRepository.getCartItems() } returns flowOf(expectedItems)

        val result = useCase().toList().first()
        assertEquals(expectedItems, result)
    }
}