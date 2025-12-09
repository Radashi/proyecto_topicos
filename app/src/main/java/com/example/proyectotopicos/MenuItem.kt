package com.example.proyectotopicos

// Data Class para el modelo de un plato
data class MenuItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String? = null // Opcional, para la imagen
)