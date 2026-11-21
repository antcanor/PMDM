package com.isengard.ejercicios

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PreferenciasActivity : AppCompatActivity() {
    private lateinit var etNombre: EditText
    private lateinit var btnGuardar: Button
    private lateinit var tvUltimoRegistro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferencias)

        etNombre = findViewById(R.id.etNombreGuardia)
        btnGuardar = findViewById(R.id.btnGuardarPref)
        tvUltimoRegistro = findViewById(R.id.tvUltimoRegistro)

        // Al pulsar el botón, guardamos de forma persistente el texto introducido
        btnGuardar.setOnClickListener {
            val nombreCentinela = etNombre.text.toString().trim()

            if (nombreCentinela.isNotEmpty()) {
                guardarPreferencia(nombreCentinela)
                actualizarPantalla()
                Toast.makeText(this, "Datos guardados en SharedPreferences", Toast.LENGTH_SHORT).show()
            } else {
                etNombre.error = "El nombre no puede estar vacío"
            }
        }
    }

    // Leemos el valor guardado cada vez que la pantalla vuelve a estar visible
    override fun onResume() {
        super.onResume()
        actualizarPantalla()
    }

    private fun guardarPreferencia(nombre: String) {
        val prefs = getSharedPreferences("RegistroTierraMedia", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("clave_centinela", nombre)
        editor.apply() // Guarda de forma asíncrona evitando congelamientos de UI
    }

    private fun actualizarPantalla() {
        val prefs = getSharedPreferences("RegistroTierraMedia", Context.MODE_PRIVATE)
        // Recuperamos el string; si no existe, devolverá el string por defecto ("Ninguno")
        val centinelaGuardado = prefs.getString("clave_centinela", "Ninguno")
        tvUltimoRegistro.text = "Último centinela guardado: $centinelaGuardado"
    }
}
