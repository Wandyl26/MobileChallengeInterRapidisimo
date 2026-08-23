package com.example.intercommerceapp.data.local.dao.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.intercommerceapp.data.local.entity.product.ProductRemoteKeysEntity

@Dao
interface ProductRemoteKeysDao {

    @Query("SELECT * FROM product_remote_keys WHERE productId = :productId")
    suspend fun getRemoteKeyByProductId(productId: Int): ProductRemoteKeysEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<ProductRemoteKeysEntity>)

    @Query("DELETE FROM product_remote_keys")
    suspend fun clearAll()
}