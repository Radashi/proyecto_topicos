package com.example.proyectotopicos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class DetailFragment : Fragment() {

    // 1. Declaración de la variable para el ViewModel
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 2. Inicialización del ViewModel
        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        val args = arguments // Obtener el Bundle de argumentos

        // Comprobar si hay argumentos y si el Fragmento fue llamado correctamente
        if (args != null) {
            // 1. Obtener las referencias a las vistas
            val nameTextView: TextView = view.findViewById(R.id.tv_detail_name)
            val descTextView: TextView = view.findViewById(R.id.tv_detail_description)
            val priceTextView: TextView = view.findViewById(R.id.tv_detail_price)
            val addToCartButton: Button = view.findViewById(R.id.btn_detail_add_to_cart)

            // 2. Extraer los datos del Bundle (usando las mismas claves)
            val itemName = args.getString(MenuFragment.ARG_ITEM_NAME)
            val itemDescription = args.getString(MenuFragment.ARG_ITEM_DESC)
            val itemPrice = args.getDouble(MenuFragment.ARG_ITEM_PRICE)
            val itemId = args.getInt(MenuFragment.ARG_ITEM_ID) // Lo usaremos para el carrito

            // 3. Llenar las vistas con los datos
            nameTextView.text = itemName
            descTextView.text = itemDescription
            priceTextView.text = String.format("Precio: € %.2f", itemPrice)

            // 4. Configurar el listener del botón Añadir al Carrito (por ahora con Toast)
            addToCartButton.setOnClickListener {
                // Reconstruimos el objeto MenuItem para pasarlo al ViewModel
                val itemToAdd = MenuItem(itemId, itemName ?: "",
                    args.getString(MenuFragment.ARG_ITEM_DESC) ?: "",
                    itemPrice)

                cartViewModel.addItemToCart(itemToAdd)
                Toast.makeText(context, "${itemToAdd.name} añadido al carrito!", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }

        } else {
            // Manejar caso de error si no hay datos
            Toast.makeText(context, "Error: No se encontró la información del plato.", Toast.LENGTH_LONG).show()
        }

        return view
    }
}