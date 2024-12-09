package com.example.deliverycliente.ui.viewmodels

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deliverycliente.data.models.CartItem

class CartViewModel : ViewModel() {

    // LiveData para observar los productos en el carrito
    private val _cartItems = MutableLiveData<MutableList<CartItem>>(mutableListOf())
    val cartItems: MutableLiveData<MutableList<CartItem>> get() = _cartItems

    // Propiedades del restaurante actual
    var restaurantId: Int? = null
    var latitude: Double? = null
    var longitude: Double? = null

    // Total a pagar
    private val _totalPrice = MutableLiveData(0.0)
    val totalPrice: LiveData<Double> get() = _totalPrice




    // Agregar producto al carrito
    fun addItemToCart(cartItem: CartItem, restaurantId: Int, latitude: Double, longitude: Double) {
        if (_cartItems.value.isNullOrEmpty()) {
            // Configurar los valores iniciales del restaurante y coordenadas
            this.restaurantId = restaurantId
            this.latitude = latitude
            this.longitude = longitude
        } else {
            if (this.restaurantId != restaurantId) {
                // Limpiar carrito si el restaurante es diferente
                _cartItems.value = mutableListOf()
                this.restaurantId = restaurantId
                this.latitude = latitude
                this.longitude = longitude
                Log.d("CartViewModel", "El carrito fue limpiado debido a un cambio de restaurante")
            }
        }

        // Verificar si el producto ya existe en el carrito
        val existingItem = _cartItems.value?.find { it.productId == cartItem.productId }
        if (existingItem != null) {
            existingItem.quantity += cartItem.quantity
        } else {
            _cartItems.value = (_cartItems.value ?: mutableListOf()).apply { add(cartItem) }
        }

        // Actualizar el precio total del carrito
        updateTotalPrice()
    }



    // Remover producto del carrito
    fun removeItemFromCart(productId: Int) {
        _cartItems.value?.removeIf { it.productId == productId }
        updateTotalPrice()
    }

    // Incrementar cantidad de un producto
    fun incrementQuantity(productId: Int) {
        _cartItems.value?.find { it.productId == productId }?.let {
            it.quantity++
            updateTotalPrice()
        }
    }

    // Decrementar cantidad de un producto
    fun decrementQuantity(productId: Int) {
        _cartItems.value?.find { it.productId == productId }?.let {
            if (it.quantity > 1) {
                it.quantity--
            } else {
                removeItemFromCart(productId)
            }
            updateTotalPrice()
        }
    }

    // Actualizar el precio total
    private fun updateTotalPrice() {
        val total = _cartItems.value?.sumOf { it.price * it.quantity } ?: 0.0
        _totalPrice.value = total
    }

    fun updateQuantity(productId: Int, delta: Int) {
        val cartItems = _cartItems.value ?: mutableListOf()

        // Buscar el producto en el carrito
        val item = cartItems.find { it.productId == productId }

        item?.let {
            it.quantity += delta // Actualizar la cantidad

            if (it.quantity <= 0) {
                // Si la cantidad es 0 o menor, eliminar el producto del carrito
                cartItems.remove(it)
                Log.d("CartViewModel", "Producto eliminado del carrito: $productId")
            }

            // Actualizar el carrito con los cambios
            _cartItems.value = cartItems
            updateTotalPrice() // Actualizar el precio total
        }
    }


    // Limpiar el carrito después de realizar un pedido
    fun clearCart() {
        _cartItems.value?.clear()
        restaurantId = null
        latitude = null
        longitude = null
        _totalPrice.value = 0.0
    }
}
