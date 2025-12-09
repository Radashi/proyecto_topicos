package com.example.proyectotopicos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MenuFragment : Fragment() {
    private lateinit var cartViewModel: CartViewModel

    companion object {
        const val ARG_ITEM_ID = "item_id"
        const val ARG_ITEM_NAME = "item_name"
        const val ARG_ITEM_DESC = "item_description"
        const val ARG_ITEM_PRICE = "item_price"
        // ... puedes añadir más claves si añades más campos
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializa el ViewModel asociado al ciclo de vida de la Activity (¡Compartido!)
        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Infla el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.menu_recycler_view)

        // 1. Crear datos de prueba (simulación de una base de datos)
        val dishes = listOf(
            MenuItem(1, "Pizza Margarita", "Clásica pizza con tomate, mozzarella y albahaca.", 12.50),
            MenuItem(2, "Hamburguesa Clásica", "Carne de res, queso, lechuga y salsa especial.", 9.99),
            MenuItem(3, "Ensalada César", "Lechuga romana, pollo a la parrilla, crutones y aderezo César.", 8.00),
            MenuItem(4, "Pasta Carbonara", "Pasta con huevo, queso Parmesano, panceta y pimienta negra.", 11.75)
            // Añade más ítems si quieres
        )

        // 2. Crear el adaptador...
        val adapter = MenuAdapter(
            dishes,
            onAddToCartClick = { item ->
                // *** USO DEL VIEWMODEL ***
                cartViewModel.addItemToCart(item)
                Toast.makeText(context, "${item.name} añadido al carrito!", Toast.LENGTH_SHORT).show()
            },
            onItemClick = { item ->
                // 1. Crear un Bundle para almacenar los datos
                val bundle = Bundle().apply {
                    putInt(ARG_ITEM_ID, item.id)
                    putString(ARG_ITEM_NAME, item.name)
                    putString(ARG_ITEM_DESC, item.description)
                    putDouble(ARG_ITEM_PRICE, item.price)
                }

                // 2. Crear una instancia del DetailFragment y adjuntar el Bundle
                val detailFragment = DetailFragment()
                detailFragment.arguments = bundle

                // 3. Reemplazar el Fragmento actual por el DetailFragment
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailFragment) // R.id.fragment_container es el contenedor de MainActivity
                    .addToBackStack(null) // Esto permite al usuario volver al MenuFragment usando el botón "Atrás"
                    .commit()
            }
        )

        // 3. Configurar el RecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context) // Muestra los ítems verticalmente

        return view

    }
}