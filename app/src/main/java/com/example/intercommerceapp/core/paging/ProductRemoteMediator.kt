package com.example.intercommerceapp.core.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.intercommerceapp.core.network.NetworkConstants
import com.example.intercommerceapp.data.local.InterCommerceDatabase
import com.example.intercommerceapp.data.local.entity.product.ProductEntity
import com.example.intercommerceapp.data.local.entity.product.ProductRemoteKeysEntity
import com.example.intercommerceapp.data.mapper.product.toEntity
import com.example.intercommerceapp.data.remote.DummyJsonApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ProductRemoteMediator @Inject constructor(
    private val api: DummyJsonApi,
    private val database: InterCommerceDatabase,
    private val query: String? = null
) : RemoteMediator<Int, ProductEntity>() {

    private val productDao = database.productDao()
    private val remoteKeysDao = database.productRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 0
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val skip = page * NetworkConstants.PAGE_SIZE
            val response = if (query.isNullOrBlank()) {
                api.getProducts(limit = NetworkConstants.PAGE_SIZE, skip = skip)
            } else {
                api.searchProducts(query = query, limit = NetworkConstants.PAGE_SIZE, skip = skip)
            }

            val products = response.products
            val endOfPaginationReached = products.isEmpty() || response.total <= skip + products.size

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    productDao.clearAll()
                    remoteKeysDao.clearAll()
                }

                val prevKey = if (page == 0) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = products.map {
                    ProductRemoteKeysEntity(
                        productId = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                productDao.insertAll(products.map { it.toEntity() })
                remoteKeysDao.insertAll(keys)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ProductEntity>
    ): ProductRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { productId ->
                remoteKeysDao.getRemoteKeyByProductId(productId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, ProductEntity>
    ): ProductRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { product -> remoteKeysDao.getRemoteKeyByProductId(product.id) }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, ProductEntity>
    ): ProductRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { product -> remoteKeysDao.getRemoteKeyByProductId(product.id) }
    }
}