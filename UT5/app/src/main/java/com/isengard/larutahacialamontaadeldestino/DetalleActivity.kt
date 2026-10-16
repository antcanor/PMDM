package com.isengard.larutahacialamontaadeldestino

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetalleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)
        val tvNombre = findViewById<TextView>(R.id.tvDetalleNombre)
        val tvPeligro = findViewById<TextView>(R.id.tvDetallePeligro)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        // Rescatamos los datos de los extras del Intent
        val nombreEtapa = intent.getStringExtra("EXTRA_NOMBRE") ?: "Etapa desconocida"
        val peligroEtapa = intent.getStringExtra("EXTRA_PELIGRO") ?: "Sin evaluar"

        // Inyectamos los datos en la vista
        tvNombre.text = "Etapa: $nombreEtapa"
        tvPeligro.text = "Nivel de Peligro: $peligroEtapa"

        // Botón para cerrar esta pantalla y regresar a la lista
        btnVolver.setOnClickListener {
            finish()
        }
    }
}