package com.example.intercommerceapp.domain.usecase

import com.example.intercommerceapp.domain.repository.CartRepository
import com.example.intercommerceapp.domain.usecase.cart.RemoveFromCartUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RemoveFromCartUseCaseTest {

    private lateinit var cartRepository: CartRepository
    private lateinit var useCase: RemoveFromCartUseCase

    @Before
    fun setUp() {
        cartRepository = mockk(relaxed = true)
        useCase = RemoveFromCartUseCase(cartRepository)
    }

    @Test
    fun `invoke removes product from cart`() = runBlocking {
        val productId = 1
        useCase(productId)
        coVerify(exactly = 1) { cartRepository.removeFromCart(productId) }
    }
}