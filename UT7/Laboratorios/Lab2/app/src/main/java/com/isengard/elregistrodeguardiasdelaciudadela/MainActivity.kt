package com.isengard.elregistrodeguardiasdelaciudadela

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnIrPrefs = findViewById<Button>(R.id.btnIrPrefs)
        val btnIrFicheros = findViewById<Button>(R.id.btnIrFicheros)

        btnIrPrefs.setOnClickListener {
            val intent = Intent(this, PreferenciasActivity::class.java)
            startActivity(intent)
        }

        btnIrFicheros.setOnClickListener {
            val intent = Intent(this, FicherosActivity::class.java)
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}