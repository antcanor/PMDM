package com.isengard.elmapamundimultilingedelatierramedia

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var seleccionado: ImageView?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gondor = findViewById<ImageView>(R.id.ivGondor);
        val rohan = findViewById<ImageView>(R.id.ivRohan);
        val mordor = findViewById<ImageView>(R.id.ivMordor);

        ;
        gondor.setOnClickListener {
            this.seleccionado = gondor
        }

        rohan.setOnClickListener{
            this.seleccionado = rohan
        }

        mordor.setOnClickListener{
            this.seleccionado = mordor
        }

        val btn = findViewById<Button>(R.id.btnSeleccionar)

        btn.setOnClickListener {
            val region = seleccionado?.contentDescription

            if (region != null) {
                Toast.makeText(this, region, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor, selecciona una región primero", Toast.LENGTH_SHORT).show()
            }
        }
    }
}