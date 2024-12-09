package com.example.deliverycliente.data.api

import androidx.room.Index
import com.example.deliverycliente.data.models.LoginResponse
import com.example.deliverycliente.data.models.Order
import com.example.deliverycliente.data.models.Restaurant
import com.example.deliverycliente.data.models.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("users/login")
    fun loginUser(@Body loginRequest: Map<String, String>): Call<LoginResponse>

    @POST("users")
    fun registerUser(@Body registerRequest: Map<String, String>): Call<Void>

    @GET("me")
    fun getUserDetails(): Call<UserResponse>

    @GET("restaurants")
    fun getRestaurants(): Call<List<Restaurant>>

    @GET("restaurants/{id}")
    fun getRestaurantDetails(@Path("id") id: Int): Call<Restaurant>

    @POST("orders")
    fun placeOrder(@Body order: HashMap<String, Any>): Call<Void>

    @GET("orders")
    fun getOrders(): Call<List<Order>>





}
