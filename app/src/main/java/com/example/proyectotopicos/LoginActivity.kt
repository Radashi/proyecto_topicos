package com.example.proyectotopicos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 1. Obtener referencias a los elementos de la UI
        val usernameEditText = findViewById<EditText>(R.id.et_username)
        val passwordEditText = findViewById<EditText>(R.id.et_password)
        val loginButton = findViewById<Button>(R.id.btn_login)

        // 2. Configurar el Listener para el botón de Login
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // 3. Simulación de la lógica de autenticación (Credenciales fijas)
            if (username == "admin" && password == "1234") {
                // **LOGIN EXITOSO**

                // Mostrar un Toast de bienvenida
                Toast.makeText(this, "¡Bienvenido, $username!", Toast.LENGTH_SHORT).show()

                // Usar un INTENT para iniciar la MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                // Opcional: Finalizar LoginActivity para que el usuario no pueda volver con 'Atrás'
                finish()

            } else {
                // **LOGIN FALLIDO**

                // Mostrar un Toast de error
                Toast.makeText(this, "Credenciales incorrectas. Intenta de nuevo.", Toast.LENGTH_LONG).show()

                // Opcional: Limpiar el campo de contraseña
                passwordEditText.text.clear()
            }
        }
    }
}