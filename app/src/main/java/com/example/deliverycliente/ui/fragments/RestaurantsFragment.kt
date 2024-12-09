package com.example.deliverycliente.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliverycliente.R
import com.example.deliverycliente.data.api.ApiClient
import com.example.deliverycliente.data.models.Restaurant
import com.example.deliverycliente.databinding.FragmentRestaurantsBinding
import com.example.deliverycliente.ui.adapters.RestaurantAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantsFragment : Fragment() {

    private var _binding: FragmentRestaurantsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.restaurantsRecyclerView.layoutManager = LinearLayoutManager(context)

        val apiService = ApiClient.getApiService()
        apiService.getRestaurants().enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(
                call: Call<List<Restaurant>>,
                response: Response<List<Restaurant>>
            ) {
                if (response.isSuccessful) {
                    val restaurants = response.body() ?: emptyList()
                    binding.restaurantsRecyclerView.adapter = RestaurantAdapter(restaurants)
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                // Manejar errores
            }


        })

        // Configurar el botón "Ver Pedidos"
        binding.viewOrdersButton.setOnClickListener {
            // Navegar al fragmento de pedidos
            findNavController().navigate(R.id.action_restaurantsFragment_to_ordersFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
