package my.android.orderfood.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MenuItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val discountedPrice: Double,
    val servingSize: String,
    val imageUrl: String
)

data class Cart(
    val items: List<CartItem> = emptyList()
) {
    val total: Double
        get() = items.sumOf { it.menuItem.price * it.quantity }

    val discountedTotal: Double
        get() = items.sumOf { it.menuItem.discountedPrice * it.quantity }
}

data class CartItem(
    val menuItem: MenuItem,
    val quantity: Int = 1
)