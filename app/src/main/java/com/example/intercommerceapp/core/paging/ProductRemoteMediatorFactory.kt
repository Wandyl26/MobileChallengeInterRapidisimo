package com.example.intercommerceapp.core.paging
import com.example.intercommerceapp.data.local.InterCommerceDatabase
import com.example.intercommerceapp.data.remote.DummyJsonApi
import javax.inject.Inject

class ProductRemoteMediatorFactory @Inject constructor(
    private val api: DummyJsonApi,
    private val database: InterCommerceDatabase
) {
    fun create(query: String? = null): ProductRemoteMediator {
        return ProductRemoteMediator(api, database, query)
    }
}