package com.example.intercommerceapp.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intercommerceapp.domain.usecase.cart.CalculateCartTotalsUseCase
import com.example.intercommerceapp.domain.usecase.cart.GetCartUseCase
import com.example.intercommerceapp.domain.usecase.cart.RemoveFromCartUseCase
import com.example.intercommerceapp.domain.usecase.cart.UpdateCartItemQuantityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val calculateCartTotalsUseCase: CalculateCartTotalsUseCase,
    private val updateCartItemQuantityUseCase: UpdateCartItemQuantityUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        observeCartItems()
    }

    private fun observeCartItems() {
        viewModelScope.launch {
            getCartUseCase().collect { items ->
                val totals = calculateCartTotalsUseCase(items)
                _uiState.update {
                    it.copy(
                        items = items,
                        totals = totals,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        viewModelScope.launch {
            updateCartItemQuantityUseCase(productId, quantity)
        }
    }

    fun removeItem(productId: Int) {
        viewModelScope.launch {
            removeFromCartUseCase(productId)
        }
    }
}