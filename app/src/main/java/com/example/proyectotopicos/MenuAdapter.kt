package com.example.proyectotopicos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(
    private val menuList: List<MenuItem>,
    private val onAddToCartClick: (MenuItem) -> Unit, // Función Lambda para manejar el clic en 'Añadir'
    private val onItemClick: (MenuItem) -> Unit // Función Lambda para manejar el clic en el ítem (para ir a detalles)
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    // Define el ViewHolder (guarda las referencias a las vistas de item_menu_dish.xml)
    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_dish_name)
        val description: TextView = itemView.findViewById(R.id.tv_dish_description)
        val price: TextView = itemView.findViewById(R.id.tv_dish_price)
        val addButton: Button = itemView.findViewById(R.id.btn_add_to_cart)

        init {
            // Manejador de clic para ir a la pantalla de detalle del plato
            itemView.setOnClickListener {
                onItemClick(menuList[adapterPosition])
            }
        }
    }

    // 1. Crea las instancias del ViewHolder (infla el layout)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu_dish, parent, false)
        return MenuViewHolder(view)
    }

    // 2. Vincula los datos con las vistas del ViewHolder
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val currentItem = menuList[position]
        holder.name.text = currentItem.name
        holder.description.text = currentItem.description
        holder.price.text = String.format("€ %.2f", currentItem.price) // Formato de moneda

        // Configura el listener del botón "Añadir"
        holder.addButton.setOnClickListener {
            onAddToCartClick(currentItem)
        }
    }

    // 3. Devuelve el número total de ítems en la lista
    override fun getItemCount() = menuList.size
}