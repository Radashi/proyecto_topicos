package com.example.proyectotopicos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {

    // 1. MutableLiveData: Esta es la lista que se puede modificar internamente
    private val _cartItems = MutableLiveData<MutableList<CartItem>>(mutableListOf())

    // 2. LiveData: Esta es la lista de sólo lectura que los Fragments 'observarán'
    val cartItems: LiveData<MutableList<CartItem>> = _cartItems

    // 3. Getter para el Total
    val totalAmount: Double
        get() = _cartItems.value?.sumOf { it.getSubtotal() } ?: 0.0

    // Función principal para añadir/actualizar un plato al carrito
    fun addItemToCart(item: MenuItem) {
        val currentList = _cartItems.value ?: mutableListOf()
        val existingItem = currentList.find { it.item.id == item.id }

        if (existingItem != null) {
            // Si el plato ya existe, incrementa la cantidad
            existingItem.quantity++
        } else {
            // Si es un plato nuevo, añádelo con cantidad 1
            currentList.add(CartItem(item, 1))
        }

        // Importante: Debemos postear el nuevo valor para notificar a todos los observadores (Fragments)
        _cartItems.value = currentList
    }

    // [Opcional] Función para limpiar el carrito (ej. al finalizar una orden)
    fun clearCart() {
        _cartItems.value = mutableListOf()
    }
}