package com.example.proyectotopicos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter
    private lateinit var totalTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1. Inicializar el ViewModel, asociado a la Activity para que sea compartido
        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.cart_recycler_view)
        totalTextView = view.findViewById(R.id.tv_total_amount)
        val checkoutButton: Button = view.findViewById(R.id.btn_checkout)

        // 2. Configurar el RecyclerView
        cartAdapter = CartAdapter()
        recyclerView.adapter = cartAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 3. Observar los cambios del LiveData del ViewModel (¡La magia de LiveData!)
        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            // Cuando la lista de LiveData cambia (porque se añadió un ítem desde otro Fragment):

            // a) Actualiza el RecyclerView con la nueva lista
            cartAdapter.submitList(items)

            // b) Actualiza el total general
            updateTotal(items)
        }

        // 4. Configurar el botón de Checkout
        checkoutButton.setOnClickListener {
            if (cartViewModel.totalAmount > 0) {
                Toast.makeText(context, "¡Pedido de € ${String.format("%.2f", cartViewModel.totalAmount)} finalizado!", Toast.LENGTH_LONG).show()
                cartViewModel.clearCart() // Limpia el carrito
            } else {
                Toast.makeText(context, "El carrito está vacío.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    // Función para recalcular y mostrar el total
    private fun updateTotal(items: List<CartItem>) {
        val total = items.sumOf { it.getSubtotal() }
        totalTextView.text = String.format("Total a pagar: € %.2f", total)
    }
}