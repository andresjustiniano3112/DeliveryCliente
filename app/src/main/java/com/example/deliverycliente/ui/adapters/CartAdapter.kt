package com.example.deliverycliente.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deliverycliente.data.models.CartItem
import com.example.deliverycliente.databinding.ItemCartBinding
import com.example.deliverycliente.ui.viewmodels.CartViewModel

class CartAdapter(
    private val items: MutableList<CartItem>,
    private val onCartUpdated: (List<CartItem>) -> Unit

) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem) {
            binding.productName.text = cartItem.name
            binding.productPrice.text = "${cartItem.price * cartItem.quantity} Bs"
            binding.quantity.text = cartItem.quantity.toString()

            binding.increaseQuantity.setOnClickListener {
                cartItem.quantity++
                notifyItemChanged(adapterPosition)
                onCartUpdated(items)
            }

            binding.decreaseQuantity.setOnClickListener {
                if (cartItem.quantity >= 1) {
                    cartItem.quantity--
                    notifyItemChanged(adapterPosition)
                    onCartUpdated(items)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size


    fun updateItems(newItems: List<CartItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

}
