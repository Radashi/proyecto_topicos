package com.example.proyectotopicos
// Clase para un ítem dentro del carrito (incluye la cantidad)
data class CartItem(
    val item: MenuItem, // El plato que se está comprando
    var quantity: Int   // La cantidad de ese plato
) {
    // Getter para calcular el subtotal fácilmente
    fun getSubtotal(): Double {
        return item.price * quantity
    }
}