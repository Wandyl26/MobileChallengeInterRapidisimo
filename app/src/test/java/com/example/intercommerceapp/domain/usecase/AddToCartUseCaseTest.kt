package com.example.intercommerceapp.domain.usecase

import com.example.intercommerceapp.domain.Product
import com.example.intercommerceapp.domain.repository.CartRepository
import com.example.intercommerceapp.domain.usecase.cart.AddToCartUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddToCartUseCaseTest {

    private lateinit var cartRepository: CartRepository
    private lateinit var useCase: AddToCartUseCase

    @Before
    fun setUp() {
        cartRepository = mockk(relaxed = true)
        useCase = AddToCartUseCase(cartRepository)
    }

    @Test
    fun `invoke adds product to cart with default quantity 1`() = runBlocking {
        val product = createProduct()
        useCase(product)
        coVerify(exactly = 1) {
            cartRepository.addToCart(match { cartItem ->
                cartItem.productId == product.id &&
                        cartItem.title == product.title &&
                        cartItem.price == product.price &&
                        cartItem.thumbnail == product.thumbnail &&
                        cartItem.quantity == 1
            })
        }
    }

    @Test
    fun `invoke adds product with specified quantity`() = runBlocking {
        val product = createProduct()
        useCase(product, quantity = 5)
        coVerify(exactly = 1) {
            cartRepository.addToCart(match { cartItem -> cartItem.quantity == 5 })
        }
    }

    private fun createProduct() = Product(
        id = 1,
        title = "Test Product",
        description = "Description",
        category = "Category",
        price = 99.99,
        discountPercentage = 10.0,
        rating = 4.5,
        stock = 100,
        brand = "Brand",
        images = listOf("img1"),
        thumbnail = "thumbnail.jpg"
    )
}
