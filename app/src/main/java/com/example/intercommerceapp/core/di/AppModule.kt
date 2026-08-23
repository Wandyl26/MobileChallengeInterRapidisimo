package com.example.intercommerceapp.core.di

import com.example.intercommerceapp.core.util.DispatchersProvider
import com.example.intercommerceapp.core.util.DispatchersProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDispatchersProvider(): DispatchersProvider = DispatchersProviderImpl()
}