package com.example.intercommerceapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.intercommerceapp.presentation.cart.CartScreen
import com.example.intercommerceapp.presentation.catalog.CatalogScreen
import com.example.intercommerceapp.presentation.productdetail.ProductDetailScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = CatalogRoute
    ) {
        composable<CatalogRoute> {
            CatalogScreen(
                onProductClick = { productId ->
                    navController.navigate(ProductDetailRoute(productId))
                },
                onCartClick = {
                    navController.navigate(CartRoute)
                }
            )
        }

        composable<ProductDetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<ProductDetailRoute>()
            ProductDetailScreen(
                onBackClick = { navController.popBackStack() },
                onCartClick = { navController.navigate(CartRoute) }
            )
        }

        composable<CartRoute> {
            CartScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}