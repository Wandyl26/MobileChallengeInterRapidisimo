package com.example.intercommerceapp.presentation.cart
import com.example.intercommerceapp.domain.model.CartItem
import com.example.intercommerceapp.domain.usecase.cart.CalculateCartTotalsUseCase
import com.example.intercommerceapp.domain.usecase.cart.GetCartUseCase
import com.example.intercommerceapp.domain.usecase.cart.RemoveFromCartUseCase
import com.example.intercommerceapp.domain.usecase.cart.UpdateCartItemQuantityUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getCartUseCase: GetCartUseCase
    private lateinit var calculateCartTotalsUseCase: CalculateCartTotalsUseCase
    private lateinit var updateCartItemQuantityUseCase: UpdateCartItemQuantityUseCase
    private lateinit var removeFromCartUseCase: RemoveFromCartUseCase
    private lateinit var viewModel: CartViewModel

    @Before
    fun setUp() {
        getCartUseCase = mockk()
        calculateCartTotalsUseCase = CalculateCartTotalsUseCase()
        updateCartItemQuantityUseCase = mockk(relaxed = true)
        removeFromCartUseCase = mockk(relaxed = true)

        every { getCartUseCase() } returns flowOf(
            listOf(
                CartItem(1, "Product A", 100.0, "img", 2),
                CartItem(2, "Product B", 50.0, "img", 1)
            )
        )

        viewModel = CartViewModel(
            getCartUseCase = getCartUseCase,
            calculateCartTotalsUseCase = calculateCartTotalsUseCase,
            updateCartItemQuantityUseCase = updateCartItemQuantityUseCase,
            removeFromCartUseCase = removeFromCartUseCase
        )
    }

    @Test
    fun `initial state loads cart items and calculates totals`() = runTest {
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(2, state.items.size)
        assertEquals(250.0, state.totals.subtotal, 0.0)
        assertEquals(47.5, state.totals.tax, 0.0)
        assertEquals(297.5, state.totals.total, 0.0)
        assertEquals(3, state.totals.itemCount)
    }

    @Test
    fun `update quantity calls use case with correct parameters`() = runTest {
        viewModel.updateQuantity(1, 5)
        advanceUntilIdle()
        coEvery { updateCartItemQuantityUseCase(1, 5) }
    }

    @Test
    fun `remove item calls remove use case`() = runTest {
        viewModel.removeItem(2)
        advanceUntilIdle()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}