package com.example.intercommerceapp.data.local.entity.product
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_remote_keys")
data class ProductRemoteKeysEntity(
    @PrimaryKey val productId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)