package com.example.deliverycliente.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.deliverycliente.R
import com.example.deliverycliente.data.models.Restaurant
import com.example.deliverycliente.databinding.ItemRestaurantBinding
import com.squareup.picasso.Picasso

class RestaurantAdapter(private val restaurants: List<Restaurant>) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    class RestaurantViewHolder(private val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: Restaurant) {
            binding.restaurantName.text = restaurant.name
            binding.restaurantAddress.text = restaurant.address
            binding.restaurantRating.text = "Rating: ${restaurant.rating}"
            Picasso.get().load(restaurant.imageUrl).into(binding.restaurantImage)

            binding.root.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt("restaurant_id", restaurant.id)
                }
                it.findNavController().navigate(R.id.restaurantDetailsFragment, bundle)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemRestaurantBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount(): Int = restaurants.size
}
