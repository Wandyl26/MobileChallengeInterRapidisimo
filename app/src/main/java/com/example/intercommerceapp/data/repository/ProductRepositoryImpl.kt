package com.example.intercommerceapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.intercommerceapp.core.network.NetworkConstants
import com.example.intercommerceapp.core.paging.ProductRemoteMediatorFactory
import com.example.intercommerceapp.data.local.InterCommerceDatabase
import com.example.intercommerceapp.data.mapper.product.toDomain
import com.example.intercommerceapp.data.mapper.product.toEntity
import com.example.intercommerceapp.data.remote.DummyJsonApi
import com.example.intercommerceapp.domain.Product
import com.example.intercommerceapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ProductRepositoryImpl @Inject constructor(
    private val api: DummyJsonApi,
    private val database: InterCommerceDatabase,
    private val remoteMediatorFactory: ProductRemoteMediatorFactory
) : ProductRepository {

    override fun getProductsPaged(query: String?): Flow<PagingData<Product>> {
        val pagingSourceFactory = { database.productDao().getPagedProducts() }
        val remoteMediator = remoteMediatorFactory.create(query)
        return Pager(
            config = PagingConfig(
                pageSize = NetworkConstants.PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = NetworkConstants.PAGE_SIZE * 2
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { productEntity -> productEntity.toDomain() }
        }
    }

    override suspend fun getProductDetail(id: Int): Product? {
        return database.productDao().getProductById(id)?.toDomain()
    }

    override suspend fun fetchAndStoreProductDetail(id: Int): Result<Product> {
        return try {
            val dto = api.getProductDetail(id)
            val entity = dto.toEntity()
            database.productDao().insertAll(listOf(entity))
            Result.success(entity.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}