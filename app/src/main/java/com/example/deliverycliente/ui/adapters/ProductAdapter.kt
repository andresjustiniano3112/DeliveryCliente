import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.deliverycliente.data.models.CartItem
import com.example.deliverycliente.databinding.ItemProductBinding
import com.example.deliverycliente.ui.viewmodels.CartViewModel
import com.squareup.picasso.Picasso

class ProductAdapter(
    private val products: List<Product>,
    private val cartViewModel: CartViewModel, // Inyectamos el CartViewModel
    private val restaurantId: Int, // ID del restaurante
    private val latitude: Double, // Latitud del restaurante
    private val longitude: Double // Longitud del restaurante

) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productName.text = product.name
            binding.productDescription.text = product.description
            binding.productPrice.text = "$${product.price}"
            Picasso.get().load(product.image).into(binding.productImage)

            // Acción del botón "Agregar al carrito"
            binding.addToCartButton.setOnClickListener {
                try {
                    // Crear un CartItem y agregarlo al carrito
                    val cartItem = CartItem(
                        productId = product.id,
                        name = product.name,
                        price = product.price
                    )
                    cartViewModel.addItemToCart(cartItem, restaurantId, latitude, longitude)
                    Toast.makeText(binding.root.context, "${product.name} agregado al carrito", Toast.LENGTH_SHORT).show()
                } catch (e: IllegalArgumentException) {
                    // Mostrar error si el producto es de otro restaurante
                    Toast.makeText(binding.root.context, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size
}
