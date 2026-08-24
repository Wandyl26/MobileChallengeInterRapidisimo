package com.example.intercommerceapp.domain.usecase

import com.example.intercommerceapp.domain.repository.CartRepository
import com.example.intercommerceapp.domain.usecase.cart.UpdateCartItemQuantityUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateCartItemQuantityUseCaseTest {

    private lateinit var cartRepository: CartRepository
    private lateinit var useCase: UpdateCartItemQuantityUseCase

    @Before
    fun setUp() {
        cartRepository = mockk(relaxed = true)
        useCase = UpdateCartItemQuantityUseCase(cartRepository)
    }

    @Test
    fun `invoke updates quantity in repository`() = runBlocking {
        val productId = 1
        val newQuantity = 5
        useCase(productId, newQuantity)
        coVerify(exactly = 1) { cartRepository.updateQuantity(productId, newQuantity) }
    }
}