package com.example.intercommerceapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.intercommerceapp.data.local.dao.cart.CartDao
import com.example.intercommerceapp.data.local.dao.product.ProductDao
import com.example.intercommerceapp.data.local.dao.product.ProductRemoteKeysDao
import com.example.intercommerceapp.data.local.entity.cart.CartItemEntity
import com.example.intercommerceapp.data.local.entity.product.ProductEntity
import com.example.intercommerceapp.data.local.entity.product.ProductRemoteKeysEntity

@Database(
    entities = [
        ProductEntity::class,
        CartItemEntity::class,
        ProductRemoteKeysEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class InterCommerceDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun productRemoteKeysDao(): ProductRemoteKeysDao
}