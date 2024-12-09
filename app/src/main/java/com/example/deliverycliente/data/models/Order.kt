package com.example.deliverycliente.data.models

import Product


data class Order(
    val id: Int,
    val user_id: Int,
    val restaurant_id: Int,
    val total: String,
    val latitude: String,
    val longitude: String,
    val address: String,
    val status: String,
    val created_at: String,
    val delivery_proof: String?,
    val order_details: List<OrderDetail>
)

data class OrderDetail(
    val id: Int,
    val quantity: Int,
    val price: String,
    val product: Product
)

