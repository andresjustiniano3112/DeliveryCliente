package com.example.deliverycliente.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliverycliente.data.api.ApiClient
import com.example.deliverycliente.data.api.ApiService
import com.example.deliverycliente.databinding.FragmentCartBinding
import com.example.deliverycliente.ui.adapters.CartAdapter
import com.example.deliverycliente.ui.viewmodels.CartViewModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter

    // Usamos activityViewModels para compartir el CartViewModel entre fragmentos
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar RecyclerView y Adapter
        cartAdapter = CartAdapter(mutableListOf()) { updatedItems ->
            cartViewModel.cartItems.value?.clear()
            cartViewModel.cartItems.value?.addAll(updatedItems)
            updateTotalPrice()
        }
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = cartAdapter


        // Observamos los cambios en los datos del carrito
        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            cartAdapter.updateItems(cartItems)
            updateTotalPrice()
        }

        // Configurar el botón para realizar el pedido
        binding.orderButton.setOnClickListener {
            val address = binding.addressInput.text.toString()
            if (address.isBlank()) {
                Toast.makeText(requireContext(), "ingrese los datos faltantes", Toast.LENGTH_SHORT).show()
            } else {
                placeOrder(address)
            }
        }
    }

    // Actualizar el precio total
    private fun updateTotalPrice() {
        val total = cartViewModel.cartItems.value?.sumOf { it.price * it.quantity } ?: 0.0
        binding.totalPrice.text = "Total a pagar: $total Bs"
    }

    // Método para realizar el pedido
    private fun placeOrder(address: String) {
        val restaurantId = cartViewModel.restaurantId
        val latitude = cartViewModel.latitude
        val longitude = cartViewModel.longitude
        val cartItems = cartViewModel.cartItems.value

        if (restaurantId == null || latitude == null || longitude == null || cartItems.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "No hay productos en el carrito", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear el payload del pedido
        val order = hashMapOf<String, Any>(
            "restaurant_id" to restaurantId!!,
            "total" to cartViewModel.totalPrice.value!!,
            "address" to address,
            "latitude" to latitude!!,
            "longitude" to longitude!!,
            "details" to cartItems.map {
                hashMapOf(
                    "product_id" to it.productId,
                    "qty" to it.quantity,
                    "price" to it.price
                )
            }
        )



        // Llamada a la API usando Retrofit
        val orderService = ApiClient.retrofit.create(ApiService::class.java)
        val call = orderService.placeOrder(order)


        call.enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: retrofit2.Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Pedido realizado con éxito", Toast.LENGTH_SHORT).show()
                    cartViewModel.clearCart()
                } else {
                    Toast.makeText(requireContext(), "Error al realizar el pedido", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                Toast.makeText(requireContext(), "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
