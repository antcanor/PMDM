package com.isengard.ejercicios

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

        // 1. Vinculamos únicamente los componentes activos en el XML
        val btnIrAudio = findViewById< Button>(R.id.btnIrAudio)
        val btnIrCampanas = findViewById<Button>(R.id.btnIrCampanas)

        val btnIrVideo = findViewById<Button>(R.id.btnIrVideo)

        val btnIrVideoWeb = findViewById<Button>(R.id.btnIrVideoWeb)

        // 2. Listener de la Práctica 1 (Operativo al 100%)
        btnIrAudio.setOnClickListener {
            val intent = Intent(this, AudioActivity::class.java)
            startActivity(intent)
        }

        // 3. Dejamos el listener del laboratorio desactivado o comentando la navegación interna
        btnIrCampanas.setOnClickListener {
            // Comentado preventivamente para evitar cierres forzados en el aula
             val intent = Intent(this, CampanasActivity::class.java)
             startActivity(intent)
        }

        btnIrVideo.setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
        }

        btnIrVideoWeb.setOnClickListener {
            val intent = Intent(this, VIdeoWebActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}