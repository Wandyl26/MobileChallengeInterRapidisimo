package com.example.intercommerceapp.data.mapper.cart

import com.example.intercommerceapp.data.local.entity.cart.CartItemEntity
import com.example.intercommerceapp.domain.model.CartItem


fun CartItemEntity.toDomain(): CartItem {
    return CartItem(
        productId = productId,
        title = title,
        price = price,
        thumbnail = thumbnail,
        quantity = quantity
    )
}

fun CartItem.toEntity(): CartItemEntity {
    return CartItemEntity(
        productId = productId,
        title = title,
        price = price,
        thumbnail = thumbnail,
        quantity = quantity
    )
}