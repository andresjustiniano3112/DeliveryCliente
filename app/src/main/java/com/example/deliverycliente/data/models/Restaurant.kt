package com.example.deliverycliente.data.models

import Product
import com.google.gson.annotations.SerializedName

data class Restaurant(
    val id: Int,
    val name: String,
    val address: String,
    val rating: Float,
    val latitude: Double,
    val longitude: Double,
    @SerializedName("logo") val imageUrl: String,
    val products: List<Product> // Añade esta línea
)
