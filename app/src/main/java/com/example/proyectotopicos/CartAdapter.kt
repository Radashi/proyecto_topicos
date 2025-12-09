package com.example.proyectotopicos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    // Lista mutable que contendrá los ítems y que se actualizará con LiveData
    private var cartItems = mutableListOf<CartItem>()

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quantity: TextView = itemView.findViewById(R.id.tv_cart_quantity)
        val name: TextView = itemView.findViewById(R.id.tv_cart_name)
        val subtotal: TextView = itemView.findViewById(R.id.tv_cart_subtotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_dish, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = cartItems[position]

        holder.quantity.text = "${currentItem.quantity}x"
        holder.name.text = currentItem.item.name

        // Muestra el subtotal del ítem
        holder.subtotal.text = String.format("Subtotal: € %.2f", currentItem.getSubtotal())
    }

    override fun getItemCount() = cartItems.size

    // Función CRUCIAL: Actualiza la lista y notifica al RecyclerView
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newCartItems: List<CartItem>) {
        // Usamos una copia de la lista que viene del LiveData
        cartItems = newCartItems.toMutableList()
        // Notifica al RecyclerView que la lista ha cambiado y debe redibujarse
        notifyDataSetChanged()
    }
}