package com.example.deliverycliente.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.deliverycliente.R
import com.example.deliverycliente.databinding.FragmentOrderDetailsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class OrderDetailsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener datos del Bundle
        val restaurantLat = arguments?.getDouble("restaurantLat") ?: 0.0
        val restaurantLng = arguments?.getDouble("restaurantLng") ?: 0.0
        val clientLat = arguments?.getDouble("clientLat") ?: 0.0
        val clientLng = arguments?.getDouble("clientLng") ?: 0.0

        val restaurantLatLng = LatLng(restaurantLat, restaurantLng)
        val clientLatLng = LatLng(clientLat, clientLng)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { map ->
            googleMap = map

            // Agregar marcadores
            googleMap.addMarker(MarkerOptions().position(restaurantLatLng).title("Restaurante"))
            googleMap.addMarker(MarkerOptions().position(clientLatLng).title("Ubicación del Pedido"))

            // Ajustar cámara
            val bounds = LatLngBounds.Builder()
                .include(restaurantLatLng)
                .include(clientLatLng)
                .build()
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
    }
}
