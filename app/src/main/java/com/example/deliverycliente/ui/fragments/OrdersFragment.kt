package com.example.deliverycliente.ui.fragments

import OrdersAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliverycliente.R
import com.example.deliverycliente.ui.viewmodels.OrdersViewModel
import com.example.deliverycliente.databinding.FragmentOrdersBinding




class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding
    private val ordersViewModel: OrdersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Coordenadas de la Universidad Nur
        val staticClientLat = -17.768858144946645
        val staticClientLng = -63.18274193152263


        // Configurar RecyclerView
        val adapter = OrdersAdapter(listOf()) { order ->
            // Crear un Bundle con los datos
            val bundle = Bundle().apply {
                putDouble("restaurantLat", order.latitude.toDouble())
                putDouble("restaurantLng", order.longitude.toDouble())
                putDouble("clientLat", staticClientLat) // Coordenadas estáticas del cliente
                putDouble("clientLng", staticClientLng) // Coordenadas estáticas del cliente
            }

            // Navegar al fragmento con los argumentos
            findNavController().navigate(R.id.orderDetailsFragment, bundle)
        }
        binding.ordersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.ordersRecyclerView.adapter = adapter

        // Observar pedidos
        ordersViewModel.orders.observe(viewLifecycleOwner) { orders ->
            adapter.updateOrders(orders)
        }

        // Obtener pedidos
        ordersViewModel.fetchOrders()
    }
}
