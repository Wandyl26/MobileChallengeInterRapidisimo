package com.example.intercommerceapp.data.repository

import com.example.intercommerceapp.core.paging.ProductRemoteMediatorFactory
import com.example.intercommerceapp.data.local.InterCommerceDatabase
import com.example.intercommerceapp.data.local.dao.product.ProductDao
import com.example.intercommerceapp.data.remote.DummyJsonApi
import com.example.intercommerceapp.data.remote.dto.ProductDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ProductRepositoryImplTest {

    private lateinit var api: DummyJsonApi
    private lateinit var database: InterCommerceDatabase
    private lateinit var productDao: ProductDao
    private lateinit var repository: ProductRepositoryImpl

    @Before
    fun setUp() {
        api = mockk()
        database = mockk()
        productDao = mockk()
        val remoteMediatorFactory = mockk<ProductRemoteMediatorFactory>()
        repository = ProductRepositoryImpl(api, database, remoteMediatorFactory)
    }

    @Test
    fun `fetchAndStoreProductDetail stores product and returns success`() = runBlocking {
        val dto = ProductDto(
            id = 1, title = "Product", description = "Desc", category = "Cat", price = 10.0,
            discountPercentage = 0.0, rating = 4.0, stock = 10, brand = "Brand",
            images = listOf("img"), thumbnail = "thumb"
        )
        coEvery { api.getProductDetail(1) } returns dto
        coEvery { database.productDao() } returns productDao
        coEvery { productDao.insertAll(any()) } returns Unit
        coEvery { productDao.getProductById(1) } returns null

        val result = repository.fetchAndStoreProductDetail(1)

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { productDao.insertAll(any()) }
    }
}