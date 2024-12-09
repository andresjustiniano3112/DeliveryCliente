package com.example.deliverycliente.data.models

data class CartItem(
    val productId: Int,
    val name: String,
    val price: Double,
    var quantity: Int = 1
)
