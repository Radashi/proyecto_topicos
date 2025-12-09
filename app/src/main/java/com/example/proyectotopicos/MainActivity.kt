package com.example.proyectotopicos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_navigation)

        // 1. Cargar el Fragment de Menú por defecto al iniciar
        loadFragment(MenuFragment())

        // 2. Listener para la navegación inferior
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_menu -> {
                    loadFragment(MenuFragment())
                    true
                }
                R.id.nav_cart -> {
                    loadFragment(CartFragment())
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    // Función para reemplazar el Fragment en el contenedor
    private fun loadFragment(fragment: Fragment) {
        // Inicia una transacción de Fragmentos
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Remplaza el contenido del contenedor
            .commit() // Ejecuta la transacción
    }
}