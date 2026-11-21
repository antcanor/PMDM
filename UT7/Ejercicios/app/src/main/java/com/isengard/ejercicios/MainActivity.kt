package com.isengard.ejercicios

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnIrPrefs = findViewById<Button>(R.id.btnIrPrefs)
        val btnIrFicheros = findViewById<Button>(R.id.btnIrFicheros)
        val btnIrExterno = findViewById<Button>(R.id.btnIrExterno)
        val btnIrSQLite = findViewById<Button>(R.id.btnIrSQLite)


        btnIrPrefs.setOnClickListener {
            val intent = Intent(this, PreferenciasActivity::class.java)
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnIrFicheros.setOnClickListener {
            val intent = Intent(this, FicherosActivity::class.java)
            startActivity(intent)
        }

        btnIrExterno.setOnClickListener {
            val intent = Intent(this, AlmacenamientoExternoActivity::class.java)
            startActivity(intent)
        }

        // 2.3. Selector de Base de Datos Relacional SQLite (Punto 4 - Próxima sesión)
        btnIrSQLite.setOnClickListener {
            val intent = Intent(this, SQLiteActivity::class.java)
            startActivity(intent)
        }
    }
}