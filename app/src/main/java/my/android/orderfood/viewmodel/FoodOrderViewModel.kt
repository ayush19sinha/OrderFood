package my.android.orderfood.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import my.android.orderfood.data.model.Cart
import my.android.orderfood.data.model.CartItem
import my.android.orderfood.data.model.MenuItem
import my.android.orderfood.data.repository.MenuData
import my.android.orderfood.data.repository.MenuRepository

class FoodOrderViewModel(private val repository: MenuRepository) : ViewModel() {
    private val _menuData = MutableStateFlow<MenuData?>(null)
    val menuData: StateFlow<MenuData?> = _menuData

    private val _cart = MutableStateFlow(Cart(emptyList()))
    val cart: StateFlow<Cart> = _cart

    init {
        loadMenuData()
    }

    private fun loadMenuData() {
        viewModelScope.launch {
            _menuData.value = repository.getMenuData()
        }
    }

    fun addToCart(menuItem: MenuItem) {
        val currentItems = _cart.value.items.toMutableList()
        val existingItem = currentItems.find { it.menuItem.id == menuItem.id }

        if (existingItem != null) {
            val index = currentItems.indexOf(existingItem)
            currentItems[index] = existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            currentItems.add(CartItem(menuItem, 1))
        }

        _cart.value = Cart(currentItems)
    }
    fun removeFromCart(menuItem: MenuItem) {
        val currentItems = _cart.value.items.toMutableList()
        val existingItem = currentItems.find { it.menuItem.id == menuItem.id } ?: return

        if (existingItem.quantity > 1) {
            val index = currentItems.indexOf(existingItem)
            currentItems[index] = existingItem.copy(quantity = existingItem.quantity - 1)
        } else {
            currentItems.remove(existingItem)
        }

        _cart.value = Cart(currentItems)
    }
}