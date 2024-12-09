package com.example.deliverycliente.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.deliverycliente.data.api.ApiClient
import com.example.deliverycliente.data.api.ApiService
import com.example.deliverycliente.data.models.Order


class OrdersViewModel : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    fun fetchOrders() {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        apiService.getOrders().enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    _orders.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                // Manejar el error
                t.printStackTrace()
            }
        })
    }
}
