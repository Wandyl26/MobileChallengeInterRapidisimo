package com.example.intercommerceapp.data.di

import com.example.intercommerceapp.data.repository.CartRepositoryImpl
import com.example.intercommerceapp.data.repository.ProductRepositoryImpl
import com.example.intercommerceapp.domain.repository.CartRepository
import com.example.intercommerceapp.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository

    @Binds
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}