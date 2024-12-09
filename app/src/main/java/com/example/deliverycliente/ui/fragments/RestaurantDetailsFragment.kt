package com.example.deliverycliente.ui.fragments

import ProductAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliverycliente.R
import com.example.deliverycliente.data.api.ApiClient
import com.example.deliverycliente.data.models.Restaurant
import com.example.deliverycliente.databinding.FragmentRestaurantDetailsBinding
import com.example.deliverycliente.ui.viewmodels.CartViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantDetailsBinding
    private lateinit var productAdapter: ProductAdapter

    // Compartimos el CartViewModel con otros fragmentos
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val restaurantId = arguments?.getInt("restaurant_id") ?: return
        getRestaurantDetails(restaurantId)
    }

    private fun getRestaurantDetails(id: Int) {
        val apiService = ApiClient.getApiService()
        apiService.getRestaurantDetails(id).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                if (response.isSuccessful) {
                    val restaurant = response.body()
                    if (restaurant != null) {
                        setupUI(restaurant)
                    } else {
                        Toast.makeText(context, "Detalles del restaurante no disponibles", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Error al obtener detalles", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupUI(restaurant: Restaurant) {
        // Establecer el nombre del restaurante
        binding.restaurantName.text = restaurant.name

        // Configurar el RecyclerView y el adaptador
        productAdapter = ProductAdapter(
            products = restaurant.products,
            cartViewModel = cartViewModel,
            restaurantId = restaurant.id,
            latitude = restaurant.latitude,
            longitude = restaurant.longitude
        )
        binding.productsRecyclerView.adapter = productAdapter
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Botón para ver carrito
        binding.viewCartButton.setOnClickListener {
            navigateToCart()
        }
    }

    private fun navigateToCart() {
        try {
            findNavController().navigate(R.id.action_restaurantDetailsFragment_to_cartFragment)
        } catch (e: Exception) {
            Toast.makeText(context, "Error al navegar al carrito: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}
